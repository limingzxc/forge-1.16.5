package com.wwwday.boson.food;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ObsidianApple extends Item {
    private static final Food food = (new Food.Builder())
            .saturationMod(10)
            .nutrition(20)
            .effect(() -> new EffectInstance(Effects.POISON, 3 * 20, 1), 1)
            .build();

    public ObsidianApple() {
        super(new Properties().food(food).tab(ItemGroup.TAB_FOOD));
    }
}
