package com.nullmine.core.items;

import com.nullmine.core.NullMine;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockManager implements Listener {
    public static BlockManager instance;

    public static BlockManager getInstance() {
        return instance;
    }

    private Map<String, Tuple<YamlConfiguration, File>> changedChunks;

    private Map<String, CustomBlock> blocks;

    private Map<String, Map<Location, CustomBlock>> blocksToParticle;
    private int bt = 1;
    private int currentBlockTick = 1;

    public BlockManager () {
        instance = this;

        new BlockBreaking();

        changedChunks = new HashMap<>();
        blocks = new HashMap<>();
        blocksToParticle = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, NullMine.getInstance());

        Bukkit.getScheduler().runTaskTimer(NullMine.getInstance(), () -> {
            if (changedChunks.size() > 4) {
                String savingChunk = (String)changedChunks.keySet().toArray()[0];
                try {
                    changedChunks.get(savingChunk).a().save(changedChunks.get(savingChunk).b());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                changedChunks.remove(savingChunk);
            }
        }, 10, 10);
        Bukkit.getScheduler().runTaskTimer(NullMine.getInstance(), () -> {
            for (String x : changedChunks.keySet()) {
                try {
                    changedChunks.get(x).a().save(changedChunks.get(x).b());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1200, 1200);
        Bukkit.getScheduler().runTaskTimer(NullMine.getInstance(), () -> {
            if (bt >= currentBlockTick) {
                currentBlockTick = 1;
                for (Map.Entry<String, Map<Location, CustomBlock>> x : blocksToParticle.entrySet()) {
                    for (Map.Entry<Location, CustomBlock> y : x.getValue().entrySet()) {
                        y.getValue().spawnParticles(bt, y.getKey().clone());
                    }
                }
                if (blocksToParticle.size() > 128) {
                    bt = 2;
                    if (blocksToParticle.size() > 256) {
                        bt = 4;
                        if (blocksToParticle.size() > 512) {
                            bt = 8;
                        }
                    }
                }
            } else {
                currentBlockTick += 1;
            }
        }, 2, 2);
    }

    public void saveBlocks() {
        for (String x : changedChunks.keySet()) {
            try {
                changedChunks.get(x).a().save(changedChunks.get(x).b());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void placeBlock (Chunk chunk, Location loc, String block) {
        String locstr = chunk.getWorld() + "_" + chunk.getX() + "_" + chunk.getZ();
        String blockloc = loc.getBlockX() + "_" + loc.getBlockY() + "_" + loc.getBlockZ();

        if (changedChunks.containsKey(locstr)) {
            YamlConfiguration config = changedChunks.get(locstr).a();
            config.set(blockloc, block);
        } else {
            File file = new File(loc.getWorld().getWorldFolder().getPath() + "/blocks/" + chunk.getX() + "_" + chunk.getZ() + ".yml");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set(blockloc, block);
            changedChunks.put(locstr, new Tuple<>(config, file));
        }
        updateChunk(chunk);
    }

    public void removeBlock (Chunk chunk, Location loc) {
        String locstr = chunk.getWorld() + "_" + chunk.getX() + "_" + chunk.getZ();
        String blockloc = loc.getBlockX() + "_" + loc.getBlockY() + "_" + loc.getBlockZ();

        if (changedChunks.containsKey(locstr)) {
            YamlConfiguration config = changedChunks.get(locstr).a();
            config.set(blockloc, null);
        } else {
            File file = new File(loc.getWorld().getWorldFolder().getPath() + "/blocks/" + chunk.getX() + "_" + chunk.getZ() + ".yml");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set(blockloc, null);
        }
    }

    public void registerBlock (CustomBlock block) {
        ItemManager.getInstance().registerItem(block);
        blocks.put(block.getId(), block);
    }

    public CustomBlock getRegisteredBlock(String str) {
        return blocks.get(str);
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        if ("block".equals(LoreInfo.getType(e.getItemInHand()))) {
            placeBlock(e.getBlock().getChunk(), e.getBlock().getLocation(), LoreInfo.getId(e.getItemInHand()));
        } else if (e.getItemInHand().getItemMeta().getLore() != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void loadChunk(ChunkLoadEvent e) {
        if (changedChunks.containsKey(e.getChunk().getWorld() + "_" + e.getChunk().getX() + "_" + e.getChunk().getZ())) {
            YamlConfiguration config = changedChunks.get(e.getChunk().getWorld() + "_" + e.getChunk().getX() + "_" + e.getChunk().getZ()).a();
            Map<Location, CustomBlock> blockss = new HashMap<>();
            for (String key : config.getKeys(false)) {
                String[] eee = key.split("_");
                Block b = e.getWorld().getBlockAt(new Location(e.getWorld(), Double.parseDouble(eee[0]) + 0.5, Double.parseDouble(eee[1]) + 0.5, Double.parseDouble(eee[2]) + 0.5));
                if (b.getRelative(BlockFace.UP).getType().isTransparent() || b.getRelative(BlockFace.DOWN).getType().isTransparent() || b.getRelative(BlockFace.EAST).getType().isTransparent() || b.getRelative(BlockFace.WEST).getType().isTransparent() || b.getRelative(BlockFace.SOUTH).getType().isTransparent() || b.getRelative(BlockFace.NORTH).getType().isTransparent()) {
                    blockss.put(b.getLocation(), getRegisteredBlock(config.getString(key)));
                }
            }
            blocksToParticle.put(e.getChunk().getWorld() + "_" + e.getChunk().getX() + "_" + e.getChunk().getZ(), blockss);
        } else {
            File file = new File(e.getWorld().getWorldFolder().getPath() + "/blocks/" + e.getChunk().getX() + "_" + e.getChunk().getZ() + ".yml");
            if (!file.exists()) {
                return;
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            Map<Location, CustomBlock> blockss = new HashMap<>();
            for (String key : config.getKeys(false)) {
                String[] eee = key.split("_");
                blockss.put(new Location(e.getWorld(), Double.parseDouble(eee[0]), Double.parseDouble(eee[1]), Double.parseDouble(eee[2])), getRegisteredBlock(config.getString(key)));
            }
            blocksToParticle.put(e.getChunk().getWorld() + "_" + e.getChunk().getX() + "_" + e.getChunk().getZ(), blockss);
        }
    }

    @EventHandler
    public void unLoadChunk(ChunkUnloadEvent e) {
        blocksToParticle.remove(e.getChunk().getWorld() + "_" + e.getChunk().getX() + "_" + e.getChunk().getZ());
    }

    public String getBlockAt(Chunk chunk, Location loc) {
        String locstr = chunk.getWorld() + "_" + chunk.getX() + "_" + chunk.getZ();
        String blockloc = loc.getBlockX() + "_" + loc.getBlockY() + "_" + loc.getBlockZ();

        if (changedChunks.containsKey(locstr)) {
            YamlConfiguration config = changedChunks.get(locstr).a();
            return config.getString(blockloc);
        } else {
            File file = new File(loc.getWorld().getWorldFolder().getPath() + "/blocks/" + chunk.getX() + "_" + chunk.getZ() + ".yml");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            changedChunks.put(locstr, new Tuple<>(config, file));
            return config.getString(blockloc);
        }
    }

    public CustomBlock getBlockClassAt(Chunk chunk, Location loc) {
        String block = getBlockAt(chunk, loc);
        if (block != null) {
            return getRegisteredBlock(block);
        } else {
            CustomBlock d = getRegisteredBlock("minecraft:" + chunk.getWorld().getBlockAt(loc).getType().toString().toLowerCase());
            if (d != null) {
                return d;
            } else {
                return getRegisteredBlock("minecraft:bedrock");
            }
        }
    }

    public void updateChunk(Chunk chunk) {
        blocksToParticle.remove(chunk.getWorld() + "_" + chunk.getX() + "_" + chunk.getZ());

        if (changedChunks.containsKey(chunk.getWorld() + "_" + chunk.getX() + "_" + chunk.getZ())) {
            YamlConfiguration config = changedChunks.get(chunk.getWorld() + "_" + chunk.getX() + "_" + chunk.getZ()).a();
            Map<Location, CustomBlock> blockss = new HashMap<>();
            for (String key : config.getKeys(false)) {
                String[] eee = key.split("_");
                Block b = chunk.getWorld().getBlockAt(new Location(chunk.getWorld(), Double.parseDouble(eee[0]), Double.parseDouble(eee[1]), Double.parseDouble(eee[2])));
                if (b.getRelative(BlockFace.UP).getType().isTransparent() || b.getRelative(BlockFace.DOWN).getType().isTransparent() || b.getRelative(BlockFace.EAST).getType().isTransparent() || b.getRelative(BlockFace.WEST).getType().isTransparent() || b.getRelative(BlockFace.SOUTH).getType().isTransparent() || b.getRelative(BlockFace.NORTH).getType().isTransparent()) {
                    blockss.put(b.getLocation(), getRegisteredBlock(config.getString(key)));
                }
            }
            blocksToParticle.put(chunk.getWorld() + "_" + chunk.getX() + "_" + chunk.getZ(), blockss);
        } else {
            File file = new File(chunk.getWorld().getWorldFolder().getPath() + "/blocks/" + chunk.getX() + "_" + chunk.getZ() + ".yml");
            if (!file.exists()) {
                return;
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            Map<Location, CustomBlock> blockss = new HashMap<>();
            for (String key : config.getKeys(false)) {
                String[] eee = key.split("_");
                blockss.put(new Location(chunk.getWorld(), Double.parseDouble(eee[0]), Double.parseDouble(eee[1]), Double.parseDouble(eee[2])), getRegisteredBlock(config.getString(key)));
            }
            blocksToParticle.put(chunk.getWorld() + "_" + chunk.getX() + "_" + chunk.getZ(), blockss);
        }
    }
}