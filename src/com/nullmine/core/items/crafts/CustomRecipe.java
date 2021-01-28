package com.nullmine.core.items.crafts;

import com.nullmine.core.items.ItemManager;
import com.nullmine.core.items.LoreInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.*;

public class CustomRecipe {

    private String[] shape;
    private ItemStack result;

    private ShapedRecipe shapedRecipe;

    private Map<Character, Material> vanillaItems;
    private Map<Character, String> customItems;

    protected String requiredResearch = null;

    public CustomRecipe(ItemStack item) {
        vanillaItems = new HashMap<>();
        customItems = new HashMap<>();

        shapedRecipe = new ShapedRecipe(item);

        result = item;
    }


    public void shape(String[] shape) {
        shapedRecipe.shape(shape);
        this.shape = shape;
    }


    public void setVanillaIngredient(char symbol, Material ingredient) {
        vanillaItems.put(symbol, ingredient);
        shapedRecipe.setIngredient(symbol, ingredient);
    }

    public void setCustomIngredient(char symbol, String id) {
        ItemStack item = ItemManager.getInstance().get(id);

        if (item == null) {
            Bukkit.getLogger().warning("Item with id '" + id + "' not found");
            return;
        }

        shapedRecipe.setIngredient(symbol, item.getData());
        customItems.put(symbol, id);
        vanillaItems.put(symbol, item.getType());
    }


    public ItemStack getResult() {
        return result;
    }


    protected String getKeyString() {
        String keys = "";
        for (String y : shape) {
            for (char x : y.toCharArray()) {
                keys += ";" + getIngredient(x);
            }
        }
        return keys;
    }


    protected String[] getShape() {
        return shape;
    }

    protected Map<Character, ItemStack> getIngredients() {
        Map<Character, ItemStack> ingredients = new HashMap<>();

            for (Map.Entry<Character, Material> e : vanillaItems.entrySet()) {
                ItemStack ingredient = customItems.containsKey(e.getKey()) ? ItemManager.getInstance().get(customItems.get(e.getKey())) : new ItemStack(e.getValue());
                ingredients.put(e.getKey(), ingredient);
            }

        return ingredients;
    }

    protected String getIngredient(char c) {
        String ingredient;
        if (customItems.containsKey(c)) {
            ingredient = customItems.get(c);
        } else if (vanillaItems.containsKey(c)) {
            ingredient = vanillaItems.get(c).toString();
        } else {
            ingredient = Material.AIR.toString();
        }

        return ingredient;
    }


    protected void register() {
        Bukkit.addRecipe(shapedRecipe);
    }

    public void setResearch(String r) {
        requiredResearch = r;
    }
}
