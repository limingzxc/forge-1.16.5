package com.wwwday.boson.tool;

import com.wwwday.boson.group.ModGroup;
import com.wwwday.boson.melee_weapons.ModItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class ObsidianPickaxe extends PickaxeItem {
    public ObsidianPickaxe() {
        super(ModItemTier.OBSIDIAN, 2, -3f, (new Item.Properties().tab(ModGroup.itemGroup)));
    }
}
