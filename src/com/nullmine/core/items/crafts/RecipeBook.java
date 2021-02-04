package com.nullmine.core.items.crafts;

import com.nullmine.core.items.LoreInfo;
import com.nullmine.core.utils.ui.ChestUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeBook extends ChestUI {
    public static RecipeBook getInstance() {
        return instance;
    }

    private static RecipeBook instance;

    private Map<String, ItemStack[]> recipes;

    public RecipeBook () {
        super("Recipe book", 6);
        instance = this;
        recipes = new HashMap<>();

        ItemStack v = new ItemStack(Material.STAINED_GLASS_PANE);
        v.setDurability((short) 15);
        ItemStack f = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemStack a = new ItemStack(Material.AIR);


        ItemStack[] inv = new ItemStack[] {
                f,v,v,v,v,v,v,v,f,
                v,a,a,a,a,a,a,a,v,
                v,a,a,a,a,a,a,a,v,
                v,a,a,a,a,a,a,a,v,
                v,a,a,a,a,a,a,a,v,
                f,v,v,v,v,v,v,v,f
        };
        getInventory().setContents(inv);

        finalizeUI(new ItemStack(Material.STAINED_GLASS_PANE));
    }

    public void open(Player player) {
        Inventory i = Bukkit.createInventory(null, 54);
        i.setContents(getInventory().getContents());
        player.openInventory(i);
    }

    @Override
    public void slotClicked(ItemStack item, int slot, Player player, Inventory inv) {
            if (item.hasItemMeta() && item.getItemMeta().hasLore() && recipes.containsKey(LoreInfo.getId(item))) {
                ItemStack[] page = recipes.get(LoreInfo.getId(item));
                inv.setContents(page);
                finalizeUI(new ItemStack(Material.STAINED_GLASS_PANE));
            }
    }

    public void addPage(String where, String prefix, String name, ItemStack item) {
        ItemStack v = new ItemStack(Material.STAINED_GLASS_PANE);
        v.setDurability((short) 15);
        ItemStack f = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemStack a = new ItemStack(Material.AIR);

        ItemMeta metaa = f.getItemMeta();
        metaa.setDisplayName(" ");
        f.setItemMeta(metaa);

        metaa = v.getItemMeta();
        metaa.setDisplayName(" ");
        v.setItemMeta(metaa);

        ItemStack c = new ItemStack(Material.ARROW);

        metaa = item.getItemMeta();
        List<String> lo = new ArrayList<>();
        lo.add("§b" + where);
        metaa.setLore(lo);
        metaa.setDisplayName("§fReturn");
        c.setItemMeta(metaa);

        ItemStack[] inv = new ItemStack[] {
            f,v,v,v,c,v,v,v,f,
            v,a,a,a,a,a,a,a,v,
            v,a,a,a,a,a,a,a,v,
            v,a,a,a,a,a,a,a,v,
            v,a,a,a,a,a,a,a,v,
            f,v,v,v,v,v,v,v,f
        };

        ItemStack fi = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta m = fi.getItemMeta();
        String lore = "";
        for (char x : "Recipe book".toCharArray()) {
            lore += "§" + x;
        }
        List<String> lr = new ArrayList<>();
        lr.add(lore);
        m.setDisplayName("");
        m.setLore(lr);
        fi.setItemMeta(m);
        inv[0] = fi;


        if (where == "main") {
            ItemStack[] in =  getInventory().getContents();
            int i = 0;
            for (ItemStack test : in) {
                if (test == null || test.getType() == Material.AIR) {
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§" + prefix + name + " crafts");
                    List<String> lor = new ArrayList<>();
                    lor.add("§b" + name);
                    meta.setLore(lor);
                    item.setItemMeta(meta);
                    in[i] = item;
                    recipes.put(name, inv);
                    break;
                }
                i++;
            }
            getInventory().setContents(in);
        } else {
            ItemStack[] in = recipes.get(where);
            int i = 0;
            for (ItemStack test : in) {
                if (test == null || test.getType() == Material.AIR) {
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§" + prefix + name + " crafts");
                    List<String> lor = new ArrayList<>();
                    lor.add("§b" + name);
                    meta.setLore(lor);
                    item.setItemMeta(meta);
                    in[i] = item;
                    recipes.put(name, inv);
                    break;
                }
                i++;
            }
            recipes.put(where, in);
        }
    }

    public void addRecipe(String where, ItemStack item, ItemStack[] its) {
        ItemStack v = new ItemStack(Material.STAINED_GLASS_PANE);
        v.setDurability((short) 15);
        ItemStack f = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemStack a = new ItemStack(Material.AIR);
        ItemStack c = new ItemStack(Material.WORKBENCH);

        ItemMeta metaa = item.getItemMeta();
        List<String> lo = new ArrayList<>();
        lo.add("§b" + where);
        metaa.setLore(lo);
        metaa.setDisplayName("§fReturn");
        c.setItemMeta(metaa);

        ItemStack[] inv = new ItemStack[] {
                f,v,v,v,c,v,v,v,f,
                v,v,v,v,v,v,v,v,v,
                v,v,a,a,a,v,v,v,v,
                v,v,a,a,a,v,a,v,v,
                v,v,a,a,a,v,v,v,v,
                f,v,v,v,v,v,v,v,f
        };

        ItemStack fi = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta m = fi.getItemMeta();
        String lore = "";
        for (char x : "Recipe book".toCharArray()) {
            lore += "§" + x;
        }
        List<String> lr = new ArrayList<>();
        lr.add(lore);
        m.setDisplayName("");
        m.setLore(lr);
        fi.setItemMeta(m);
        inv[0] = fi;

        inv[20] = its[0];
        inv[21] = its[1];
        inv[22] = its[2];

        inv[29] = its[3];
        inv[30] = its[4];
        inv[31] = its[5];

        inv[38] = its[6];
        inv[39] = its[7];
        inv[40] = its[8];


        inv[33] = item;

        if (where == "main") {
            ItemStack[] in =  getInventory().getContents();
            int i = 0;
            for (ItemStack test : in) {
                if (test == null || test.getType() == Material.AIR) {
                    if (item.getItemMeta().hasLore()) {
                        in[i] = item;
                    } else {
                        ItemStack it = item.clone();

                        ItemMeta meta = item.getItemMeta();
                        List<String> lor = new ArrayList<>();
                        lor.add("§b" + it.getType().toString());
                        meta.setLore(lor);
                        item.setItemMeta(meta);
                        in[i] = it;
                    }
                    recipes.put(LoreInfo.getId(item), inv);
                    break;
                }
                i++;
            }
            getInventory().setContents(in);
        } else {
            ItemStack[] in = recipes.get(where);
            int i = 0;
            for (ItemStack test : in) {
                if (test == null || test.getType() == Material.AIR) {
                    if (item.getItemMeta().hasLore()) {
                        in[i] = item;
                    } else {
                        ItemStack it = item.clone();

                        ItemMeta meta = item.getItemMeta();
                        List<String> lor = new ArrayList<>();
                        lor.add("§b" + it.getType().toString());
                        meta.setLore(lor);
                        item.setItemMeta(meta);
                        in[i] = it;
                    }
                    recipes.put(LoreInfo.getId(item), inv);
                    break;
                }
                i++;
            }
            recipes.put(where, in);
        }
    }

    public void finishRecipeBook() {
        recipes.put("main", getInventory().getContents());
    }
}