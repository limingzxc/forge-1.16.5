package com.wwwday.boson.block;

import com.wwwday.boson.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ObsidianTrashBlock extends Block {

    public ObsidianTrashBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.OBSIDIAN_TRASH_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void stepOn(World world, BlockPos blockPos, Entity entity) {
        if(entity instanceof ItemEntity && ((ItemEntity) entity).getItem().getItem().equals(Items.COBBLESTONE))
        {
            entity.remove();
        }
        super.stepOn(world, blockPos, entity);
    }
}
