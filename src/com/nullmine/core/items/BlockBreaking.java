package com.nullmine.core.items;

import com.nullmine.core.NullMine;
import com.nullmine.core.utils.DreamingPlayer;
import com.nullmine.core.utils.PacketWizard;
import io.netty.channel.*;
import io.netty.util.concurrent.Future;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class BlockBreaking implements Listener {
    private List<DreamingPlayer> currentlyBreakingBlocks;
    public BlockBreaking () {
        currentlyBreakingBlocks = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(this, NullMine.getInstance());

        Bukkit.getScheduler().runTaskTimer(NullMine.getInstance(), () -> {
            List<DreamingPlayer> toRemove = new ArrayList<>();
            try {
                for (DreamingPlayer x : currentlyBreakingBlocks) {
                    x.ticksGoneBy += 1;
                    PacketWizard.playBlockBreakAnimation(x.player, x.ticksGoneBy * 9 / x.ticksNeeded, new BlockPosition(x.breakingBlockLocation.getX(), x.breakingBlockLocation.getY(), x.breakingBlockLocation.getZ()));
                    if (x.ticksGoneBy >= x.ticksNeeded) {
                        CustomBlock block = BlockManager.getInstance().getBlockClassAt(x.breakingBlockLocation.getChunk(), x.breakingBlockLocation);

                        ItemStack drop = block.getDrop(x.breakingBlockLocation.getWorld().getBlockAt(x.breakingBlockLocation).getData());
                        x.breakingBlockLocation.getWorld().getBlockAt(x.breakingBlockLocation).setType(Material.AIR);
                        BlockManager.getInstance().removeBlock(x.breakingBlockLocation.getChunk(), x.breakingBlockLocation);
                        BlockManager.getInstance().updateChunk(x.breakingBlockLocation.getChunk());
                        toRemove.add(x);

                        PacketWizard.sendParticle(EnumParticle.BLOCK_DUST, x.breakingBlockLocation.add(0.5, 0.5, 0.5), 50, 0.3f, 0.1f, block.getItem().getTypeId());

                        if (x.willDrop) {
                            x.breakingBlockLocation.getWorld().dropItem(x.breakingBlockLocation, drop);
                        }
                    }
                }
            } catch (ConcurrentModificationException c) {
            }
            currentlyBreakingBlocks.removeAll(toRemove);
        }, 1, 1);
    }

        public void addPlayer(Player pl) {
            ChannelDuplexHandler handler = new ChannelDuplexHandler() {
                @Override
                public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                    if (packet instanceof PacketPlayInBlockDig) {
                        PacketPlayInBlockDig pa = (PacketPlayInBlockDig) packet;
                        PacketPlayInBlockDig.EnumPlayerDigType digType = pa.c();
                        if (digType.equals(PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK)) {
                            DreamingPlayer p = NullMine.getInstance().getPlayer(pl.getUniqueId());
                            currentlyBreakingBlocks.remove(p);
                            if (p.breakingBlockLocation != null) {
                            PacketWizard.playBlockBreakAnimation(p.player, 10, new BlockPosition(p.breakingBlockLocation.getX(), p.breakingBlockLocation.getY(), p.breakingBlockLocation.getZ()));
                            }
                        } else if (digType.equals(PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK)) {
                        DreamingPlayer p = NullMine.getInstance().getPlayer(pl.getUniqueId());
                        currentlyBreakingBlocks.remove(p);
                        if (p.breakingBlockLocation != null) {
                            PacketWizard.playBlockBreakAnimation(p.player, 10, new BlockPosition(p.breakingBlockLocation.getX(), p.breakingBlockLocation.getY(), p.breakingBlockLocation.getZ()));
                        }
                    } else if (digType.equals(PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK)) {
                    Location loc = new Location(pl.getWorld(), pa.a().getX(), pa.a().getY(), pa.a().getZ());

                    DreamingPlayer player = NullMine.getInstance().getPlayer(pl.getUniqueId());

                    CustomBlock block = BlockManager.getInstance().getBlockClassAt(loc.getChunk(), loc);

                    if (block.getHardness() < 0) {
                        return;
                    }
                    player.willDrop = true;
                    player.breakingBlockLocation = loc;
                    player.ticksGoneBy = 0;
                    player.ticksNeeded = block.getHardness();
                    if (block.getTool().equals(LoreInfo.getType(pl.getItemInHand())) || "multi tool".equals(LoreInfo.getType(pl.getItemInHand()))) {
                        player.ticksNeeded /= LoreInfo.getMiningSpeed(pl.getItemInHand());
                        if (block.getToolLevel() > LoreInfo.getMiningLevel(pl.getItemInHand())) {
                            player.ticksNeeded *= 5;
                            player.willDrop = false;
                        }
                    } else if (block.getToolLevel() != 0) {
                            player.willDrop = false;
                    }
                    currentlyBreakingBlocks.add(player);
                }
            }
                    super.channelRead(channelHandlerContext, packet);
                }

                @Override
                public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
                    super.write(channelHandlerContext, packet, channelPromise);
                }
            };

            ChannelPipeline pipeline = ((CraftPlayer) pl).getHandle().playerConnection.networkManager.channel.pipeline();
            pipeline.addBefore("packet_handler", pl.getName(), handler);
        }

        public void removePlayer(Player player) {
            Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
            Future<Object> submit = channel.eventLoop().submit(() -> {
                channel.pipeline().remove(player.getName());
                return null;
            });
        }


        @EventHandler
        public void onJoin(PlayerJoinEvent e) {
            addPlayer(e.getPlayer());
        }

        @EventHandler
        public void onQuit(PlayerQuitEvent e) {
            removePlayer(e.getPlayer());
        }


    @EventHandler
    public void itemConsumeEvent(PlayerItemConsumeEvent e) {
        if (e.getPlayer().getItemInHand().getType() == Material.MILK_BUCKET) {
            Bukkit.getScheduler().runTaskLater(NullMine.getInstance(), () -> {
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 100));
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, -1));
            }, 1);
        }
    }

    @EventHandler
    public void onBlockBreakHOW(BlockBreakEvent e) {
        e.setCancelled(true);
    }
}