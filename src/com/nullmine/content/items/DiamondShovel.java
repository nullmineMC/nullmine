package com.nullmine.content.items;

import com.nullmine.core.items.CustomItem;
import com.nullmine.core.items.crafts.CraftingManager;
import com.nullmine.core.items.crafts.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiamondShovel extends CustomItem {
    public DiamondShovel() {
        setId("diamond_shovel");
        setItem(new ItemStack(Material.DIAMOND_SPADE));
        setType(new String[] {
                "shovel",
                "12",
                "4",
                "6"
        });
        CustomRecipe recipe = new CustomRecipe(getFinalItemStack());
        recipe.shape(new String[]{" d ", " s ", " s "});
        recipe.setVanillaIngredient('d', Material.DIAMOND);
        recipe.setVanillaIngredient('s', Material.GOLD_INGOT);

        CraftingManager.getInstance().registerRecipe(recipe);
    }
}
