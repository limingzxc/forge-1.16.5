package com.wwwday.boson.redstone;

import com.wwwday.boson.ModBlockStateProperties;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;


public class Nand extends RedstoneDiodeBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LEFT = ModBlockStateProperties.LEFT;
    public static final BooleanProperty RIGHT = ModBlockStateProperties.RIGHT;

    public Nand(Properties p_i48416_1_) {
        super(p_i48416_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(POWERED, Boolean.TRUE).setValue(LEFT, Boolean.FALSE).setValue(RIGHT, Boolean.FALSE));
    }

    @Override
    protected int getDelay(BlockState state) {
        return 2;
    }

    public boolean[] getSignalBoth(World world, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        Direction direction1 = direction.getClockWise();
        Direction direction2 = direction.getCounterClockWise();
        return new boolean[]{getInputSignal(world, pos.relative(direction1), direction1) > 0, getInputSignal(world, pos.relative(direction2), direction2) > 0};
    }

    protected int getInputSignal(World world, BlockPos pos, Direction direction) {
        int i = world.getSignal(pos, direction);
        if (i >= 15) {
            return i;
        } else {
            BlockState blockstate = world.getBlockState(pos);
            return Math.max(i, blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedstoneWireBlock.POWER) : 0);
        }
    }

    @Override
    protected boolean shouldTurnOn(World world, BlockPos pos, BlockState state) {
        boolean[] i = getSignalBoth(world, pos, state);

        boolean flag = state.getValue(POWERED);
        boolean flag1 = !(i[0] && i[1]);

        boolean left = state.getValue(LEFT);
        boolean right = state.getValue(RIGHT);

        return flag != flag1 || i[0] != left || i[1] != right;
    }

    private void refreshOutputState(World world, BlockPos pos, BlockState state) {
        boolean[] i = getSignalBoth(world, pos, state);

        boolean left = state.getValue(LEFT);
        boolean right = state.getValue(RIGHT);

        boolean flag = !(i[0] && i[1]);

        if(i[0] != left || i[1] != right) {
            world.setBlock(pos, state.setValue(LEFT, i[0]).setValue(RIGHT, i[1]).setValue(POWERED, flag), 2);
        }

        this.updateNeighborsInFront(world, pos, state);
    }

    @Override
    protected void checkTickOnNeighbor(World world, BlockPos pos, BlockState state) {
        if (shouldTurnOn(world, pos, state) && !world.getBlockTicks().willTickThisTick(pos, this)) {
            TickPriority tickpriority = TickPriority.HIGH;
            if (this.shouldPrioritize(world, pos, state)) {
                tickpriority = TickPriority.EXTREMELY_HIGH;
            } else if (state.getValue(POWERED)) {
                tickpriority = TickPriority.VERY_HIGH;
            }

            world.getBlockTicks().scheduleTick(pos, this, this.getDelay(state), tickpriority);
        }

    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
        boolean[] i = getSignalBoth(world, pos, state);
        if (i[0] || i[1]) {
            world.getBlockTicks().scheduleTick(pos, this, 1);
        }

    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        refreshOutputState(world, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING, POWERED, LEFT, RIGHT);
    }

    @Override
    public void onNeighborChange(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, BlockPos neighbor) {
        if (pos.getY() == neighbor.getY() && world instanceof World && !world.isClientSide()) {
            state.neighborChanged((World)world, pos, world.getBlockState(neighbor).getBlock(), neighbor, false);
        }
    }
}
