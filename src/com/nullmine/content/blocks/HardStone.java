package com.nullmine.content.blocks;

import com.nullmine.core.items.CustomBlock;
import com.nullmine.core.utils.PacketWizard;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HardStone extends CustomBlock {
    public HardStone() {
        setId("hard_stone");
        setItem(new ItemStack(Material.COBBLESTONE));
        setToolLevel(2);
        setTool("pickaxe");
        setHardness(1800);
        setDisplayname("§fHard stone");
    }

    @Override
    public void spawnParticles(int btp, Location loc) {
        PacketWizard.sendParticle(EnumParticle.DRIP_LAVA, loc.add(0.5,0.5,0.5), 3, 0.4f, 1f/btp);
    }
}
