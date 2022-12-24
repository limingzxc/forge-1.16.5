package com.wwwday.boson.item;

import com.wwwday.boson.ModItems;
import com.wwwday.boson.group.ModGroup;
import com.wwwday.boson.inventory.BackpackInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Backpack extends Item {

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(54);

    public Backpack() {
        super(new Item.Properties().stacksTo(1).tab(ModGroup.itemGroup));
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        if(!pLevel.isClientSide()) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if(itemStack.getItem() == ModItems.BACKPACK.get()) {
                BackpackInventory.openGUI((ServerPlayerEntity) pPlayer, pPlayer.inventory.getSelected());
            }
        }
        return super.use(pLevel, pPlayer, pHand);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {

        return new ICapabilityProvider()
        {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                {
                    return LazyOptional.of(() -> itemStackHandler).cast();
                }
                return Backpack.super.getDefaultInstance().getCapability(cap, side);
            }
        };
    }
}
