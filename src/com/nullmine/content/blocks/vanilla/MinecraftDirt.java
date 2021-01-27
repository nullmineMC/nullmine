package com.nullmine.content.blocks.vanilla;

import com.nullmine.core.items.CustomBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinecraftDirt extends CustomBlock {

    public MinecraftDirt() {
        setId("minecraft:dirt");
        setItem(new ItemStack(Material.DIRT));
        setToolLevel(0);
        setTool("shovel");
        setHardness(40);
        setVanilla(true);
    }
}
