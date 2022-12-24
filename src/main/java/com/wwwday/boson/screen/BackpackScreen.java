package com.wwwday.boson.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wwwday.boson.ModItems;
import com.wwwday.boson.container.BackpackContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BackpackScreen extends ContainerScreen<BackpackContainer> {

    private static final ResourceLocation GUI = new ResourceLocation("textures/gui/container/generic_54.png");

    private final BackpackContainer pMenu;
    private final int pId;

    public BackpackScreen(BackpackContainer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.pMenu = pMenu;
        this.pId = pPlayerInventory.selected;
        this.passEvents = false;
        this.imageHeight = 114 + 6 * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pX, int pY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int containerRows = 6;
        this.blit(pMatrixStack, i, j, 0, 0, this.imageWidth, containerRows * 18 + 17);
        this.blit(pMatrixStack, i, j + containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
    }

    @Override
    protected void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
        if(pSlot != null && pSlot.getSlotIndex() == pId && pSlot.container == inventory && pType != ClickType.CLONE)
        {
            return;
        }
        super.slotClicked(pSlot, pSlotId, pMouseButton, pType);
    }

    @Override
    public void tick() {
        if(inventory.getSelected().getItem() != ModItems.BACKPACK.get())
        {
            pMenu.removed(inventory.player);
            inventory.player.closeContainer();
        }
        super.tick();
    }
}
