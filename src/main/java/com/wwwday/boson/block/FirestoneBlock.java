package com.wwwday.boson.block;

import com.wwwday.boson.item.Firestone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;

public class FirestoneBlock extends Block {

    public FirestoneBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if(!world.isClientSide) {
            if(hand == Hand.MAIN_HAND) {
                System.out.println("I right-click a FirestoneBlock. Called for the Main Hand!");
            }
            if(hand == Hand.OFF_HAND) {
                System.out.println("I right-click a FirestoneBlock. Called for the Off Hand!");
            }
        }
        return super.use(blockState, world, blockPos, playerEntity, hand, blockRayTraceResult);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void attack(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        if(!world.isClientSide) {
            System.out.println("I left-click a FirestoneBlock!");
        }
    }

    @Override
    public void stepOn(World world, BlockPos blockPos, Entity entity) {
        Firestone.lightEntityOnFire(entity, 5);
        super.stepOn(world, blockPos, entity);
    }
}
