package com.wwwday.boson.tileentity;

import com.wwwday.boson.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class LightningChannelerTile extends TileEntity {

    private final ItemStackHandler itemStackHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemStackHandler);

    public LightningChannelerTile(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemStackHandler.deserializeNBT(nbt.getCompound("inv"));
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.put("inv", itemStackHandler.serializeNBT());
        return super.save(compound);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0: return stack.getItem() == Items.GLASS_PANE;
                    case 1: return stack.getItem() == ModItems.OBSIDIAN_INGOT.get() ||
                            stack.getItem() == ModItems.FIRESTONE.get();
                    default:
                        return false;
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap);
    }

    public void lightningHasStruck() {
        boolean hasFocusInFirstSlot = this.itemStackHandler.getStackInSlot(0).getCount() > 0
                && this.itemStackHandler.getStackInSlot(0).getItem() == Items.GLASS_PANE;
        boolean hasMaterialInSecondSlot = this.itemStackHandler.getStackInSlot(1).getCount() > 0
                && this.itemStackHandler.getStackInSlot(1).getItem() == ModItems.OBSIDIAN_INGOT.get();

        if(hasFocusInFirstSlot && hasMaterialInSecondSlot) {
            this.itemStackHandler.getStackInSlot(0).shrink(1);
            this.itemStackHandler.getStackInSlot(1).shrink(1);

            this.itemStackHandler.insertItem(1, new ItemStack(ModItems.FIRESTONE.get()), false);
        }
    }
}
