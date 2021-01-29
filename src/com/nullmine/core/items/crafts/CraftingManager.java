package com.nullmine.core.items.crafts;

import com.nullmine.core.NullMine;
import com.nullmine.core.items.LoreInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.*;

import java.util.*;

public class CraftingManager implements Listener {

    private static CraftingManager instance;

    private Map<String, ItemStack> recipes;

    public CraftingManager() {
        instance = this;

        recipes = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, NullMine.getInstance());
    }


    public void registerRecipe(CustomRecipe recipe) {
        recipe.register();
        recipes.put(recipe.getKeyString(), recipe.getResult());

        RecipeBook.getInstance().addRecipe(recipe.category, recipe.getResult(), recipe.getItems());

    }

    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent e) {
        CraftingInventory inventory = e.getInventory();

        String keys = "";
        for (ItemStack i : inventory.getMatrix()) {
            if (LoreInfo.getId(i) == null) {
                if (i != null) {
                    keys += ";" + i.getType().toString();
                }
            } else {
                keys += ";" + LoreInfo.getId(i);
            }
        }
        ItemStack r = recipes.get(keys);
        if (r != null) {
            inventory.setResult(r);
        } else {
            inventory.setResult(new ItemStack(Material.AIR));
        }

    }

    public static CraftingManager getInstance() {
        return instance;
    }
}
