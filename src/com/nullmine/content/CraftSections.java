package com.nullmine.content;

import com.nullmine.core.items.crafts.RecipeBook;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CraftSections {
    public static void initialize() {
        RecipeBook.getInstance().addPage("main", "f", "Vanilla", new ItemStack(Material.DIAMOND_ORE));
        RecipeBook.getInstance().addPage("Vanilla", "f", "Vanilla tools", new ItemStack(Material.IRON_AXE));
        RecipeBook.getInstance().addPage("Vanilla tools", "b", "Vanilla diamond tools", new ItemStack(Material.DIAMOND_PICKAXE));
    }
}
