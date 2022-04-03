package com.wwwday.boson.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.wwwday.boson.Boson;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class ReturnHomeCommand {
    public ReturnHomeCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("home")
                .then(Commands.literal("return").executes((command) -> {
                    return returnHome(command.getSource());
                })));
    }

    private int returnHome(CommandSource source) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getPlayerOrException();
        boolean hasHome = player.getPersistentData().getIntArray(Boson.MOD_ID + "homepos").length != 0;

        if(hasHome) {
            int[] playerPos = player.getPersistentData().getIntArray(Boson.MOD_ID + "homepos");
            player.moveTo(playerPos[0] + 0.5, playerPos[1], playerPos[2] + 0.5);

            source.sendSuccess(new StringTextComponent("Player returned Home!"), true);
            return 1;
        } else {
            source.sendSuccess(new StringTextComponent("No Home Position has been set!"), true);
            return -1;
        }
    }
}
