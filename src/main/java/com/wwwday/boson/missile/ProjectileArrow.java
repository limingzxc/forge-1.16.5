package com.wwwday.boson.missile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProjectileArrow extends Item {
    public ProjectileArrow() {
        super(new Item.Properties().tab(ItemGroup.TAB_MISC));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound((PlayerEntity)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            ArrowEntity arrowEntity = new ArrowEntity(pLevel, pPlayer);
            arrowEntity.shootFromRotation(pPlayer, pPlayer.xRot, pPlayer.yRot, 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(arrowEntity);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
