package com.wwwday.boson.missile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.SnowballEntity;

import javax.annotation.Nonnull;


public class ItemInfiniteSnowball extends Item {
    public ItemInfiniteSnowball() {
        super(new Properties().tab(ItemGroup.TAB_MISC));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getItemInHand(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            SnowballEntity snowball = new SnowballEntity(world, player);
            snowball.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 1.5F, 1.0F);
            world.addFreshEntity(snowball);
        }

        // item.shrink(1);  数量 - 1
        return new ActionResult<>(ActionResultType.SUCCESS, item);
    }
}
