package com.wwwday.boson.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wwwday.boson.Boson;
import com.wwwday.boson.container.LightningChannelerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class LightningChannelerScreen extends ContainerScreen<LightningChannelerContainer> {
    private final ResourceLocation GUI = new ResourceLocation(Boson.MOD_ID,
            "textures/gui/lightning_channeler_gui.png");

    public LightningChannelerScreen(LightningChannelerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if(menu.isLightningStorm()) {
            this.blit(matrixStack, i + 82, i + 9, 176, 0, 13, 17);
        }
    }
}
