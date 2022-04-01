package com.wwwday.boson.crop;

import com.wwwday.boson.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class Soybean extends Item {
    private static final Food food = (new Food.Builder())
            .saturation(1)
            .hunger(1)
            .setAlwaysEdible()
            .fastToEat()
            .effect(() -> new EffectInstance(Effects.LEVITATION, 10 * 20, 1), 1)
            .effect(() -> new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1), 0.1F)
            .build();

    public Soybean() {
        super(new Properties().food(food).group(ItemGroup.FOOD));
    }

    private final Block plantBlock = Blocks.FARMLAND;
    private final Block plants = ModBlocks.SOYBEAN_BLOCK.get();

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        PlayerEntity player =  itemUseContext.getPlayer();
        Hand hand = itemUseContext.getHand();
        World worldIn = itemUseContext.getWorld();
        BlockPos pos = itemUseContext.getPos();
        Direction direction = itemUseContext.getFace();

        assert player != null;
        ItemStack itemstack = player.getHeldItem(hand);
        BlockState blockstate = worldIn.getBlockState(pos);

        if (blockstate.getBlock() == this.plantBlock && direction == Direction.UP && player.canPlayerEdit(pos.offset(direction), direction, itemstack)) {

            worldIn.setBlockState(pos.up(), this.plants.getDefaultState());

            if (player instanceof ServerPlayerEntity) {

                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos.up(), itemstack);
            }

            itemstack.shrink(1);
            return ActionResultType.SUCCESS;
        }

        else {

            return ActionResultType.FAIL;
        }
    }
}
