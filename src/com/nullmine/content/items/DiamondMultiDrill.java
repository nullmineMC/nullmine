package com.nullmine.content.items;

import com.nullmine.core.items.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiamondMultiDrill extends CustomItem {
    public DiamondMultiDrill() {
        setDisplayname("§fDiamond multi tool");
        setId("diamond_multi_tool");
        setItem(new ItemStack(Material.PRISMARINE_SHARD));
        setType(new String[] {
                "multi_tool",
                "16",
                "4",
                "8"
        });
    }
}
