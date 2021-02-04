package com.nullmine.core;

import com.nullmine.content.Blocks;
import com.nullmine.content.CraftSections;
import com.nullmine.content.Items;
import com.nullmine.core.Guilds.GuildManager;
import com.nullmine.core.Guilds.GuildMenu;
import com.nullmine.core.Guilds.GuildNPC;
import com.nullmine.core.items.BlockManager;
import com.nullmine.core.items.CommandCustomGive;
import com.nullmine.core.items.ItemManager;
import com.nullmine.core.items.crafts.CraftingManager;
import com.nullmine.core.items.crafts.RecipeBook;
import com.nullmine.core.items.crafts.RecipeBookCommand;
import com.nullmine.core.utils.CustomPlayer;
import com.nullmine.core.utils.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.*;

public class NullMine extends JavaPlugin implements Listener {
    private static NullMine instance;
    public static NullMine getInstance() {
        return instance;
    }
    public static List<String> playerNames = new ArrayList<>();

    private Map<UUID, CustomPlayer> playerMap;

    @Override
    public void onEnable() {
        instance = this;
        playerMap = new HashMap<>();
        playerNames = new ArrayList<>();

        new ItemManager();
        new BlockManager();
        new CraftingManager();
        new RecipeBook();

        CraftSections.initialize();
        Items.initialize();
        Blocks.initialize();

        new CommandCustomGive();
        new RecipeBookCommand();

        new GuildMenu();
        new GuildNPC();
        new GuildManager();

        new PacketHandler();

        Bukkit.getPluginManager().registerEvents(this, this);

        RecipeBook.getInstance().finishRecipeBook();
        System.gc();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
        BlockManager.getInstance().saveBlocks();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
        e.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 100));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, -1));

        playerNames.add(e.getPlayer().getName());
        CustomPlayer pl = new CustomPlayer();
        pl.player = e.getPlayer();
        playerMap.put(e.getPlayer().getUniqueId(), pl);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./data/players/" + e.getPlayer().getUniqueId() + ".yml"));

        pl.guild = config.getString("guild");
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        playerNames.remove(e.getPlayer().getName());
        playerMap.remove(e.getPlayer().getUniqueId());
    }

    public Map<UUID, CustomPlayer> getPlayerMap() {
        return playerMap;
    }

    public CustomPlayer getPlayer(UUID e) {
        return playerMap.get(e);
    }
}
