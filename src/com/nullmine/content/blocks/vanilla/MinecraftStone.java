package com.nullmine.content.blocks.vanilla;

import com.nullmine.core.items.CustomBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinecraftStone extends CustomBlock {

    public MinecraftStone() {
        setId("minecraft:stone");
        setItem(new ItemStack(Material.STONE));
        setToolLevel(1);
        setTool("pickaxe");
        setHardness(160);
    }

    @Override
    public ItemStack getDrop(short damage) {
        if (damage == 0) {
            return new ItemStack(Material.COBBLESTONE);
        }
        else {
            ItemStack item = new ItemStack(Material.STONE);
            item.setDurability(damage);

            return item;
        }
    }

}
