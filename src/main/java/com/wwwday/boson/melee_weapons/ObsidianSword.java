package com.wwwday.boson.melee_weapons;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;

public class ObsidianSword extends SwordItem {
    public ObsidianSword() {
        super(ModItemTier.OBSIDIAN,3,-2.4F, new Item.Properties().tab(ItemGroup.TAB_COMBAT));
    }
}
