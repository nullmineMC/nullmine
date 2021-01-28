package com.nullmine.core.items;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {
    private String id;
    private ItemStack item;
    /**
     *  needed for tools
     */
    private String[] type;

    private String displayName;

    public ItemStack getFinalItemStack() {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(LoreInfo.getLore(id, type));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (displayName != null) {
            meta.setDisplayName(displayName.replace("&", "§"));
        }
        item.setItemMeta(meta);
        return item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
