package com.nullmine.content.items;

import com.nullmine.core.items.CustomItem;
import com.nullmine.core.items.crafts.CraftingManager;
import com.nullmine.core.items.crafts.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiamondMultiDrill extends CustomItem {
    public DiamondMultiDrill() {
        setDisplayName("§fDiamond multi tool");
        setId("diamond_multi_tool");
        setItem(new ItemStack(Material.PRISMARINE_SHARD));
        setType(new String[] {
                "multi_tool",
                "16",
                "4",
                "8"
        });

        CustomRecipe recipe = new CustomRecipe(getFinalItemStack());
        recipe.shape(new String[]{" p ", "sga", " g "});
        recipe.setCustomIngredient('p', "diamond_pickaxe");
        recipe.setCustomIngredient('s', "diamond_shovel");
        recipe.setCustomIngredient('a', "diamond_axe");
        recipe.setVanillaIngredient('g', Material.GOLD_INGOT);
        recipe.category = "Vanilla diamond tools";

        CraftingManager.getInstance().registerRecipe(recipe);
    }
}
