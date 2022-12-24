package com.wwwday.boson.inventory;

import com.wwwday.boson.ModItems;
import com.wwwday.boson.container.BackpackContainer;
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

    private final ItemStackHandler itemStackHandler = createHandler();
    private final ItemStack itemStack;

    private final PlayerEntity playerEntity;

    public BackpackInventory(ItemStack stack, PlayerEntity player) {
        playerEntity = player;
        itemStack = stack;


        this.loadItems(stack.getOrCreateTag());
    }

    public static void openGUI(ServerPlayerEntity player, ItemStack stack) {
        NetworkHooks.openGui(player, new BackpackInventory(stack, player));
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.boson.backpack");
            }

    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BackpackContainer(i, playerInventory,
                itemStackHandler, 6);
    }

    public ItemStackHandler createHandler() {
        return new ItemStackHandler(54) {
            @Override
            protected void onContentsChanged(int slot)
            {
                setDataChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack)
            {
                return stack.getItem() != ModItems.BACKPACK.get();
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
