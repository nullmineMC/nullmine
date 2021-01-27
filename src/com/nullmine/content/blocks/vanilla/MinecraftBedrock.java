package com.nullmine.content.blocks.vanilla;

import com.nullmine.core.items.CustomBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinecraftBedrock extends CustomBlock {

    public MinecraftBedrock() {
        setId("minecraft:bedrock");
        setItem(new ItemStack(Material.BEDROCK));
        setToolLevel(2);
        setTool("pickaxe");
        setHardness(-1);
    }

}
