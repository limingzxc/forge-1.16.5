package com.wwwday.boson.item;

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
    private final int pRows;

    public Backpack(int pRows) {
        super(new Item.Properties().stacksTo(1).tab(ModGroup.itemGroup));
        this.pRows = pRows;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if(!pLevel.isClientSide()) {
            if(itemStack.getItem() instanceof Backpack) {
                BackpackInventory.openGUI((ServerPlayerEntity) pPlayer, itemStack, pRows);
            }
        }
        return ActionResult.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    public int getRows() {
        return pRows;
    }
}
