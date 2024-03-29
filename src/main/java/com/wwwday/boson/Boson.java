package com.wwwday.boson;

import com.google.common.collect.ImmutableMap;
import com.wwwday.boson.container.ModContainers;
import com.wwwday.boson.screen.BackpackScreen;
import com.wwwday.boson.screen.LightningChannelerScreen;
import com.wwwday.boson.screen.TestScreen;
import com.wwwday.boson.tileentity.ModTileEntities;
import com.wwwday.boson.world.structures.ModStructures;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.AxeItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Boson.MOD_ID)
public class Boson {

    public static final String MOD_ID = "boson";

//    private static final Logger LOGGER = LogManager.getLogger();

    public Boson() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModTileEntities.register(eventBus);
        ModContainers.register(eventBus);

        ModStructures.register(eventBus);

        eventBus.addListener(this::doClientStuff);

        eventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AxeItem.STRIPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPABLES)
                    .put(ModBlocks.POPLAR_LOG.get(), ModBlocks.STRIPPED_POPLAR_LOG.get())
                    .put(ModBlocks.POPLAR_WOOD.get(), ModBlocks.STRIPPED_POPLAR_WOOD.get()).build();
            ModStructures.setupStructures();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.SOYBEAN_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.POPLAR_LEAVES.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.POPLAR_SAPLING.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.NAND.get(), RenderType.cutout());

            ScreenManager.register(ModContainers.LIGHTNING_CHANNELER_CONTAINER.get(),
                    LightningChannelerScreen::new);

            ScreenManager.register(ModContainers.BIG_BACKPACK_CONTAINER.get(),
                    BackpackScreen::new);

            ScreenManager.register(ModContainers.SMALL_BACKPACK_CONTAINER.get(),
                    BackpackScreen::new);

            ScreenManager.register(ModContainers.TEST_CONTAINER.get(),
                    TestScreen::new);
        });
    }
}
