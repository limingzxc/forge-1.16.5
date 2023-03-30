package com.wwwday.boson.item;

import com.wwwday.boson.container.ModContainers;
import com.wwwday.boson.container.TestContainer;
import com.wwwday.boson.group.ModGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestCapability extends Item {

    public TestCapability() {
        super(new Properties().tab(ModGroup.itemGroup));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, @Nonnull Hand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if(!pLevel.isClientSide()) {
            if(itemStack.getItem() instanceof TestCapability) {
                NetworkHooks.openGui((ServerPlayerEntity) pPlayer, containerSupplier);
            }
        }
        return ActionResult.sidedSuccess(itemStack, pLevel.isClientSide);
    }



    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {

        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    CompoundNBT itemStackTag = stack.getOrCreateTag(); // 获取或创建 itemStack 的 nbt
                    if (!itemStackTag.contains("ItemStackHandler")) { // 检查是否已存在存储的 nbt
                        ItemStackHandler itemStackHandler = new ItemStackHandler(54);
                        itemStackTag.put("ItemStackHandler", itemStackHandler.serializeNBT()); // 存储 ItemStackHandler 的 nbt
                    }
                    ItemStackHandler itemStackHandler = new ItemStackHandler(54);
                    itemStackHandler.deserializeNBT(itemStackTag.getCompound("ItemStackHandler")); // 从 nbt 中获取存储的 ItemStackHandler
                    return LazyOptional.of(() -> itemStackHandler).cast();
                }
                return LazyOptional.empty();
            }
        };
    }

    INamedContainerProvider containerSupplier = new INamedContainerProvider() {
        @Nonnull
        @Override
        public ITextComponent getDisplayName() {
            return new TranslationTextComponent("screen.boson.backpack");
        }

        @Nonnull
        @Override
        public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
            return new TestContainer(ModContainers.TEST_CONTAINER.get(), i, playerInventory,
                    playerInventory.getSelected());
        }
    };
}
