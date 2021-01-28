package com.nullmine.content.blocks.vanilla;

import com.nullmine.core.items.CustomBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinecraftCoalOre extends CustomBlock {

    public MinecraftCoalOre() {
        setId("minecraft:coal_ore");
        setItem(new ItemStack(Material.COAL_ORE));
        setToolLevel(0);
        setTool("pickaxe");
        setHardness(260);
        setFortunable(true);
    }
    @Override
    public ItemStack getDrop(short m) {
        return new ItemStack(Material.COAL);
    }
}
