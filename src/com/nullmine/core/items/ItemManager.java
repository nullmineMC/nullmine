package com.nullmine.core.items;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {
    private static ItemManager instance;
    public static ItemManager getInstance() {
        return instance;
    }

    private Map<String, ItemStack> items;

    public ItemManager() {
        instance = this;
        items = new HashMap<>();
    }

    public List<String> getIds() {
        return new ArrayList<>(items.keySet());
    }

    public ItemStack get(String id) {
        return items.get(id);
    }

    public void registerItem(CustomItem item) {
        items.put(item.getId(), item.getFinalItemStack());
    }
}
