package com.nullmine.core.Guilds;

import com.nullmine.core.NullMine;
import com.nullmine.core.items.LoreInfo;
import com.nullmine.core.utils.ui.ChestUI;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

public class GuildMenu extends ChestUI {
    public static GuildMenu getInstance() {
        return instance;
    }

    private static GuildMenu instance;

    private ItemStack[] noGuild;
    private ItemStack[] yesGuild;

    private String checkingString = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM_+$#@%&1234567890?!π¡";

    public GuildMenu() {
        super("Recipe book", 5);
        instance = this;

        ItemStack v = new ItemStack(Material.STAINED_GLASS_PANE);
        v.setDurability((short) 15);
        ItemStack f = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemStack a = new ItemStack(Material.AIR);

        ItemMeta metaa = f.getItemMeta();
        metaa.setDisplayName(" ");
        f.setItemMeta(metaa);

        metaa = v.getItemMeta();
        metaa.setDisplayName(" ");
        v.setItemMeta(metaa);

        ItemStack c = new ItemStack(Material.ANVIL);

        metaa = c.getItemMeta();
        metaa.setDisplayName("§bCreate a guild");
        c.setItemMeta(metaa);

        ItemStack j = new ItemStack(Material.DIAMOND_BLOCK);

        metaa = j.getItemMeta();
        metaa.setDisplayName("§bJoin a guild");
        j.setItemMeta(metaa);

        ItemStack d = new ItemStack(Material.SIGN);

        metaa = d.getItemMeta();
        metaa.setDisplayName("§bTo start you need to join or create a guild!");
        d.setItemMeta(metaa);

        ItemStack w = new ItemStack(Material.GRASS);

        metaa = w.getItemMeta();
        metaa.setDisplayName("§bGuild's worlds");
        w.setItemMeta(metaa);

        ItemStack g = new ItemStack(Material.NETHER_STAR);

        metaa = w.getItemMeta();
        metaa.setDisplayName("§bGuild's info");
        g.setItemMeta(metaa);

        ItemStack i = new ItemStack(Material.PAPER);

        metaa = i.getItemMeta();
        metaa.setDisplayName("§bInvite player");
        i.setItemMeta(metaa);


        noGuild = new ItemStack[] {
                f,v,v,v,v,v,v,v,f,
                v,a,a,a,d,a,a,a,v,
                v,a,c,a,a,a,j,a,v,
                v,a,a,a,a,a,a,a,v,
                f,v,v,v,v,v,v,v,f
        };
        yesGuild = new ItemStack[] {
                f,v,v,v,v,v,v,v,f,
                v,a,a,a,g,a,a,a,v,
                v,a,w,a,a,a,i,a,v,
                v,a,a,a,a,a,a,a,v,
                f,v,v,v,v,v,v,v,f
        };

        getInventory().setContents(noGuild);

        finalizeUI(new ItemStack(Material.STAINED_GLASS_PANE));
    }

    public void open(Player player) {
        Inventory i = Bukkit.createInventory(null, 45);
        String g = NullMine.getInstance().getPlayer(player.getUniqueId()).guild;
        if (g == null) {
            i.setContents(getInventory().getContents());
            player.openInventory(i);
        } else {
            i.setContents(yesGuild);
            ItemStack guildinf = i.getItem(13);

            ItemMeta metaa = guildinf.getItemMeta();
            List<String> lore = new ArrayList<>();

            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./data/guilds/" + g + "/guild.yml"));

            lore.add("§3Guild's name: §b" + g);
            lore.add("§3Guild's owner: §b" + Bukkit.getPlayer(UUID.fromString(config.getString("owner"))).getDisplayName());

            metaa.setLore(lore);
            guildinf.setItemMeta(metaa);

            i.setItem(13, guildinf);
            i.setItem(0, getInventory().getItem(0));

            player.openInventory(i);
        }
    }

    @Override
    public void slotClicked(ItemStack item, int slot, Player player, Inventory inv) {
        if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equals("§bCreate a guild")) {
            EntityPlayer nmspl = ((CraftPlayer)player).getHandle();

            PacketPlayOutOpenSignEditor open = new PacketPlayOutOpenSignEditor(new BlockPosition(0, 48, 0));
            nmspl.playerConnection.sendPacket(open);
        } else if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equals("§bInvite player")) {
            EntityPlayer nmspl = ((CraftPlayer)player).getHandle();

            PacketPlayOutOpenSignEditor open = new PacketPlayOutOpenSignEditor(new BlockPosition(0, 47, 0));
            nmspl.playerConnection.sendPacket(open);
        }
    }

    public void getSignEditPacket(PacketPlayInUpdateSign p, Player pl) {
        if (p.a().getX() == 0 && pl.getWorld().getName().equals("spawn") && p.a().getX() == 0) {
            System.out.println(p.a().getY());
            if (p.a().getY() == 48) {
                if (NullMine.getInstance().getPlayer(pl.getUniqueId()).guild == null) {
                    if (p.b()[0].c().length() < 16) {
                        for (char c : p.b()[0].c().toCharArray()) {
                            if (!checkingString.contains("" + c)) {
                                pl.sendMessage("§4The guild name may not contain \"" + c + "\"!");
                                return;
                            }
                        }
                        GuildManager.getInstance().createGuild(p.b()[0].c(), pl);
                    } else {
                        pl.sendMessage("§4The guild name is too long!");
                    }
                }
            } else if (p.a().getY() == 47) {
                if (Bukkit.getPlayer(p.b()[0].c()) != null) {
                    if (NullMine.getInstance().getPlayer(pl.getUniqueId()).guild == null) {
                        return;
                    }
                    ItemStack invite = new ItemStack(Material.PAPER);
                    ItemMeta m = invite.getItemMeta();

                    Date validTill = Calendar.getInstance().getTime();
                    validTill.setTime((long) (validTill.getTime() + 4.608e+8));

                    m.setLore(LoreInfo.getLore("invite", new String[] {"invitation", p.b()[0].c(), validTill.toString(), NullMine.getInstance().getPlayer(pl.getUniqueId()).guild}));
                    m.setDisplayName("§3Guild invite to:§b " + NullMine.getInstance().getPlayer(pl.getUniqueId()).guild);
                    invite.setItemMeta(m);
                    pl.getInventory().addItem(invite);

                } else {
                    pl.sendMessage("It appears that you are trying to invite a player which does not exist");
                }

            }
        }
    }
}