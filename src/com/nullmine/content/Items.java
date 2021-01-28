package com.nullmine.content;


import com.nullmine.content.items.DiamondAxe;
import com.nullmine.content.items.DiamondMultiDrill;
import com.nullmine.content.items.DiamondPickaxe;
import com.nullmine.content.items.DiamondShovel;
import com.nullmine.core.items.ItemManager;

public class Items {
    public static void initialize() {
        //Vanilla
        ItemManager.getInstance().registerItem(new DiamondPickaxe());
        ItemManager.getInstance().registerItem(new DiamondShovel());
        ItemManager.getInstance().registerItem(new DiamondAxe());


        ItemManager.getInstance().registerItem(new DiamondMultiDrill());
    }
}
