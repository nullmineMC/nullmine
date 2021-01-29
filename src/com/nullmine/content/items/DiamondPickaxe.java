package com.nullmine.content.items;

import com.nullmine.core.items.CustomItem;
import com.nullmine.core.items.crafts.CraftingManager;
import com.nullmine.core.items.crafts.CustomRecipe;
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
        CustomRecipe recipe = new CustomRecipe(getFinalItemStack());
        recipe.shape(new String[]{"ddd", " s ", " s "});
        recipe.setVanillaIngredient('d', Material.DIAMOND);
        recipe.setVanillaIngredient('s', Material.GOLD_INGOT);
        recipe.category = "Vanilla diamond tools";

        CraftingManager.getInstance().registerRecipe(recipe);
    }
}
