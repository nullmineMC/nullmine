package com.nullmine.content.items;

import com.nullmine.core.items.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiamondPickaxe extends CustomItem {
    public DiamondPickaxe () {
        setId("diamond_pickaxe");
        setItem(new ItemStack(Material.DIAMOND_PICKAXE));
        setType(new String[] {
                "pickaxe",
                "12",
                "4",
                "6"
        });
    }
}
