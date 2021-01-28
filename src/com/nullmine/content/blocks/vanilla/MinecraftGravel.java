package com.nullmine.content.blocks.vanilla;

import com.nullmine.core.items.CustomBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinecraftGravel extends CustomBlock {

    public MinecraftGravel() {
        setId("minecraft:gravel");
        setItem(new ItemStack(Material.GRAVEL));
        setToolLevel(0);
        setTool("shovel");
        setHardness(40);
    }
}
