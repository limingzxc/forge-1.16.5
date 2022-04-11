package com.wwwday.boson.redstone;

import com.wwwday.boson.ModBlockStateProperties;
import com.wwwday.boson.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
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

    public boolean[] getSignal(IWorldReader p_176407_1_, BlockPos p_176407_2_, BlockState p_176407_3_) {
        Direction direction = p_176407_3_.getValue(FACING);
        Direction direction1 = direction.getClockWise();
        Direction direction2 = direction.getCounterClockWise();
        return new boolean[]{getAlternateSignalAt(p_176407_1_, p_176407_2_.relative(direction1), direction1) > 0, getAlternateSignalAt(p_176407_1_, p_176407_2_.relative(direction2), direction2) > 0};
    }

    @Override
    protected boolean shouldTurnOn(World world, BlockPos pos, BlockState state) {
        boolean[] i = getSignal(world, pos, state);
        return !(i[0] && i[1]);
    }

    private void refreshOutputState(World world, BlockPos pos, BlockState state) {
        boolean[] i = getSignal(world, pos, state);

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
        if (!this.isLocked(world, pos, state)) {
            boolean flag = state.getValue(POWERED);
            boolean flag1 = this.shouldTurnOn(world, pos, state);
            boolean[] i = getSignal(world, pos, state);
            boolean left = state.getValue(LEFT);
            boolean right = state.getValue(RIGHT);

            if ((flag != flag1 || i[0] != left || i[1] != right) && !world.getBlockTicks().willTickThisTick(pos, this)) {
                TickPriority tickpriority = TickPriority.HIGH;
                if (this.shouldPrioritize(world, pos, state)) {
                    tickpriority = TickPriority.EXTREMELY_HIGH;
                } else if (flag) {
                    tickpriority = TickPriority.VERY_HIGH;
                }

                world.getBlockTicks().scheduleTick(pos, this, this.getDelay(state), tickpriority);
            }

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
    public boolean getWeakChanges(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos) {
        return state.is(ModBlocks.NAND.get());
    }

    @Override
    public void onNeighborChange(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, BlockPos neighbor) {
        if (pos.getY() == neighbor.getY() && world instanceof World && !((World)world).isClientSide()) {
            state.neighborChanged((World)world, pos, world.getBlockState(neighbor).getBlock(), neighbor, false);
        }
    }
}
