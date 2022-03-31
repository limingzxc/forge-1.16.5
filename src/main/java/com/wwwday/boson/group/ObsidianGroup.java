package com.wwwday.boson.group;

import com.wwwday.boson.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ObsidianGroup extends ItemGroup {
    public ObsidianGroup() {
        super("obsidian_group");
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.OBSIDIAN_INGOT.get());
    }

}
