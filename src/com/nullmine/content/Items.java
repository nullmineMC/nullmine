package com.nullmine.content;


import com.nullmine.content.items.DiamondMultiDrill;
import com.nullmine.content.items.DiamondPickaxe;
import com.nullmine.core.items.ItemManager;

public class Items {
    public static void initialize() {
        ItemManager.getInstance().registerItem(new DiamondPickaxe());
        ItemManager.getInstance().registerItem(new DiamondMultiDrill());
    }
}
