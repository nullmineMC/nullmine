package com.nullmine.content.blocks.vanilla;

import com.nullmine.core.items.CustomBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinecraftCobbleStone extends CustomBlock {

    public MinecraftCobbleStone() {
        setId("minecraft:cobblestone");
        setItem(new ItemStack(Material.COBBLESTONE));
        setToolLevel(1);
        setTool("pickaxe");
        setHardness(140);
    }
}
