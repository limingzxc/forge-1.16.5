package com.wwwday.boson;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
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
        ModItems.ITEMS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);

        eventBus.addListener(this::doClientStuff);

        eventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AxeItem.STRIPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPABLES)
                    .put(ModBlocks.POPLAR_LOG.get(), ModBlocks.STRIPPED_POPLAR_LOG.get())
                    .put(ModBlocks.POPLAR_WOOD.get(), ModBlocks.STRIPPED_POPLAR_WOOD.get()).build();
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.SOYBEAN_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.POPLAR_LEAVES.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.POPLAR_SAPLING.get(), RenderType.cutout());
        });
    }
}
