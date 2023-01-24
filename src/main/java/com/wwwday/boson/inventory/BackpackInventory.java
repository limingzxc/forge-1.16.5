package com.wwwday.boson.inventory;

import com.wwwday.boson.ModItems;
import com.wwwday.boson.container.BackpackContainer;
import com.wwwday.boson.item.Backpack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BackpackInventory implements INamedContainerProvider {

    private final int rows;
    private final ItemStackHandler itemStackHandler;
    private final ItemStack itemStack;
    private final PlayerEntity playerEntity;

    public BackpackInventory(ItemStack stack, PlayerEntity player, int pRows) {
        rows = pRows;
        playerEntity = player;
        itemStack = stack;
        itemStackHandler = createHandler();

        this.loadItems(stack.getOrCreateTag());
    }

    public static void openGUI(ServerPlayerEntity player, ItemStack stack, int pRows) {
        NetworkHooks.openGui(player, new BackpackInventory(stack, player, pRows));
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.boson.backpack");
            }

    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if(rows == 3){
            return BackpackContainer.threeRows(i, playerInventory,
                    itemStackHandler, playerEntity.getMainHandItem().getItem() == ModItems.SMALL_BACKPACK.get());
        }
        else {
            return BackpackContainer.sixRows(i, playerInventory,
                    itemStackHandler, playerEntity.getMainHandItem().getItem() == ModItems.BIG_BACKPACK.get());
        }

    }

    public ItemStackHandler createHandler() {
        return new ItemStackHandler(rows * 9) {
            @Override
            protected void onContentsChanged(int slot)
            {
                setDataChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack)
            {
                return !(stack.getItem() instanceof Backpack);
            }

        };
    }

    public void loadItems(CompoundNBT compound)
    {
        this.itemStackHandler.deserializeNBT(compound.getCompound("Inventory"));
    }

    public void saveItems(CompoundNBT compound)
    {
        compound.put("Inventory", this.itemStackHandler.serializeNBT());
    }

    public void setDataChanged()
    {
        if(playerEntity.level.isClientSide()) return;

        saveItems(itemStack.getOrCreateTag());
    }

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

}
