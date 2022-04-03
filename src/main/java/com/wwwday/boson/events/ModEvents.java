package com.wwwday.boson.events;

import com.wwwday.boson.Boson;
import com.wwwday.boson.commands.ReturnHomeCommand;
import com.wwwday.boson.commands.SetHomeCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;


@Mod.EventBusSubscriber(modid = Boson.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if(!event.getOriginal().level.isClientSide) {
            event.getPlayer().getPersistentData().putIntArray(Boson.MOD_ID + "homepos",
                    event.getOriginal().getPersistentData().getIntArray(Boson.MOD_ID + "homepos"));
        }
    }

}
