package com.wwwday.boson.world;

import com.wwwday.boson.Boson;
import com.wwwday.boson.world.gen.ModOreGeneration;
import com.wwwday.boson.world.gen.ModTreeGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Boson.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModOreGeneration.generateOres(event);
        ModTreeGeneration.generateTrees(event);
    }
}
