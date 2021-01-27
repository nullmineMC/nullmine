package com.nullmine.core.items;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LoreInfo {
    public static List<String> getLore(String id, String[] type) {
        List<String> lore = new ArrayList<>();

        if (type != null) {
            switch (type[0]) {
                case "pickaxe":
                    lore.add("§3Type:§b pickaxe");
                    lore.add("§3Mining speed:§b " + type[1]);
                    lore.add("§3Mining level:§b " + type[2]);
                    lore.add("§3Damage:§b " + type[3]);
                    break;
                case "axe":
                    lore.add("§3Type:§b axe");
                    lore.add("§3Mining speed:§b " + type[1]);
                    lore.add("§3Mining level:§b " + type[2]);
                    lore.add("§3Damage:§b " + type[3]);
                    break;
                case "shovel":
                    lore.add("§3Type:§b shovel");
                    lore.add("§3Mining speed:§b " + type[1]);
                    lore.add("§3Mining level:§b " + type[2]);
                    lore.add("§3Damage:§b " + type[3]);
                    break;
                case "multi_tool":
                    lore.add("§3Type:§b multi tool");
                    lore.add("§3Mining speed:§b " + type[1]);
                    lore.add("§3Mining level:§b " + type[2]);
                    lore.add("§3Damage:§b " + type[3]);
                    break;
                case "sword":
                    lore.add("§3Type:§b sword");
                    lore.add("§3Damage:§b " + type[2]);
                    break;
                case "block":
                    lore.add("§3Type:§b block");
                    break;
            }
        } else {
            lore.add("§3Type:§b item");
        }

        lore.add("");

        lore.add("§b" + id);

        return(lore);
    }
    public static String getType(ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            return item.getItemMeta().getLore().get(0).split(" ", 2)[1];
        } else {
            return null;
        }
    }

    public static String getId(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            return item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1).split("b", 2)[1];
        } else {
            return null;
        }
    }

    public static int getMiningSpeed(ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            if (lore.get(1).startsWith("§3Mining speed")) {
                return Integer.parseInt(item.getItemMeta().getLore().get(1).split(" ", 3)[2]);
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    public static int getMiningLevel(ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            if (lore.get(2).startsWith("§3Mining level")) {
                return Integer.parseInt(item.getItemMeta().getLore().get(2).split(" ", 3)[2]);
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

}
