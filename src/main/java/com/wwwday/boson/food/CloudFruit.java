package com.wwwday.boson.food;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CloudFruit extends Item {
    private static final Food food = (new Food.Builder())
            .saturationMod(1)
            .nutrition(1)
            .alwaysEat()
            .fast()
            .effect(() -> new EffectInstance(Effects.LEVITATION, 10 * 20, 1), 1)
            .build();
    public CloudFruit() {
        super(new Properties().food(food).tab(ItemGroup.TAB_FOOD));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.boson.cloud_fruit_shift"));
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.boson.cloud_fruit"));
        }
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        if(this.isEdible()) {
            if(entity.getEffect(Effects.LEVITATION) != null) {
                entity.addEffect(new EffectInstance(Effects.HARM, 1, 0));
            }
            return entity.eat(world, stack);
        }
        else {
            return stack;
        }
    }
}
