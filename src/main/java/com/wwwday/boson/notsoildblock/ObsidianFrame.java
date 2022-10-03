package com.wwwday.boson.notsoildblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;

public class ObsidianFrame extends Block {
    private static final VoxelShape shape;

    static {
        VoxelShape base = Block.box(0, 0, 0, 16, 1, 16);
        VoxelShape column1 = Block.box(0, 1, 0, 1, 15, 1);
        VoxelShape column2 = Block.box(15, 1, 0, 16, 15, 1);
        VoxelShape column3 = Block.box(0, 1, 15, 1, 15, 16);
        VoxelShape column4 = Block.box(15, 1, 15, 16, 15, 16);
        VoxelShape top = Block.box(0, 15, 0, 16, 16, 16);
        shape = VoxelShapes.or(base, column1, column2, column3, column4, top);
    }

    public ObsidianFrame() {
        super(Properties.of(Material.STONE).strength(5).noOcclusion());
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return shape;
    }
}
