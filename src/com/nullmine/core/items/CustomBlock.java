package com.nullmine.core.items;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomBlock extends CustomItem {
    private int hardness;
    private int toolLevel;
    private String tool;
    private boolean isFortunable;

    public void spawnParticles(int btps, Location loc) {};
    public ItemStack getDrop(short damage) {return getItem();}

    @Override
    public ItemStack getFinalItemStack() {
        if (!getId().startsWith("minecraft")) {
            ItemMeta meta = getItem().getItemMeta();
            meta.setLore(LoreInfo.getLore(getId(), new String[]{"block"}));
            getItem().setItemMeta(meta);
            if (getDisplayname() != null) {
                meta.setDisplayName(getDisplayname().replace("&", "§"));
            }
        }
        return getItem();
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    public int getToolLevel() {
        return toolLevel;
    }

    public void setToolLevel(int toolLevel) {
        this.toolLevel = toolLevel;
    }

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public boolean isFortunable() {
        return isFortunable;
    }

    public void setFortunable(boolean fortunable) {
        isFortunable = fortunable;
    }
}
