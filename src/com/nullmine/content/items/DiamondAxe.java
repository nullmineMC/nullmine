package com.nullmine.content.items;

import com.nullmine.core.items.CustomItem;
import com.nullmine.core.items.crafts.CraftingManager;
import com.nullmine.core.items.crafts.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiamondAxe extends CustomItem {
    public DiamondAxe() {
        setId("diamond_axe");
        setItem(new ItemStack(Material.DIAMOND_AXE));
        setType(new String[] {
                "axe",
                "12",
                "4",
                "6"
        });
        CustomRecipe recipe = new CustomRecipe(getFinalItemStack());
        recipe.shape(new String[]{" dd", " sd", " s "});
        recipe.setVanillaIngredient('d', Material.DIAMOND);
        recipe.setVanillaIngredient('s', Material.GOLD_INGOT);
        recipe.category = "Vanilla diamond tools";

        CraftingManager.getInstance().registerRecipe(recipe);
    }
}
