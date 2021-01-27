package com.nullmine.content;

import com.nullmine.content.blocks.HardStone;
import com.nullmine.content.blocks.vanilla.MinecraftBedrock;
import com.nullmine.content.blocks.vanilla.MinecraftCobbleStone;
import com.nullmine.content.blocks.vanilla.MinecraftDirt;
import com.nullmine.content.blocks.vanilla.MinecraftStone;
import com.nullmine.core.items.BlockManager;

public class Blocks {
    public static void initialize() {
        BlockManager.getInstance().registerBlock(new HardStone());

        //vanilla blocks
        BlockManager.getInstance().registerBlock(new MinecraftBedrock());
        BlockManager.getInstance().registerBlock(new MinecraftStone());
        BlockManager.getInstance().registerBlock(new MinecraftDirt());
        BlockManager.getInstance().registerBlock(new MinecraftCobbleStone());
    }
}
