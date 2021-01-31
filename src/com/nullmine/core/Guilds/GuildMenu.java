package com.nullmine.core.Guilds;

import com.nullmine.core.items.LoreInfo;
import com.nullmine.core.utils.ui.ChestUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildMenu extends ChestUI {
    public static GuildMenu getInstance() {
        return instance;
    }

    private static GuildMenu instance;

    private ItemStack[] noGuild;

    public GuildMenu() {
        super("Recipe book", 5);
        instance = this;

        ItemStack v = new ItemStack(Material.STAINED_GLASS_PANE);
        v.setDurability((short) 15);
        ItemStack f = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemStack a = new ItemStack(Material.AIR);

        ItemStack c = new ItemStack(Material.ANVIL);

        ItemMeta metaa = c.getItemMeta();
        metaa.setDisplayName("§fCreate a guild");
        c.setItemMeta(metaa);

        ItemStack j = new ItemStack(Material.DIAMOND_BLOCK);

        metaa = j.getItemMeta();
        metaa.setDisplayName("§fJoin a guild");
        j.setItemMeta(metaa);


        noGuild = new ItemStack[] {
                f,v,v,v,v,v,v,v,f,
                v,a,a,a,a,a,a,a,v,
                v,a,c,a,a,a,j,a,v,
                v,a,a,a,a,a,a,a,v,
                f,v,v,v,v,v,v,v,f
        };
        getInventory().setContents(noGuild);

        finalizeUI(new ItemStack(Material.STAINED_GLASS_PANE));
    }

    public void open(Player player) {
        Inventory i = Bukkit.createInventory(null, 45);
        String g = GuildManager.getInstance().getPlayersGuild(player);
        if (g == null) {
            i.setContents(getInventory().getContents());
            player.openInventory(i);
        }
    }

    @Override
    public void slotClicked(ItemStack item, int slot, Player player, Inventory inv) {

    }
}