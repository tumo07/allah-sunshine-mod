package com.allah;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllahSunshineMod implements DedicatedServerModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("allah-sunshine");

    @Override
    public void onInitializeServer() {
        LOGGER.info("[AllahSunshine] Initializing...");

        CommandRegistrationCallback.EVENT.register(
            (CommandDispatcher<CommandSourceStack> dispatcher,
             CommandBuildContext registryAccess,
             Commands.CommandSelection environment) -> {

                dispatcher.register(
                    Commands.literal("allah_sunshine")
                        .executes(AllahSunshineMod::executeSunshine)
                );

                LOGGER.info("[AllahSunshine] Commands registered: /allah_sunshine");
            }
        );
    }
    
    /**
     * Handles /allah_sunshine
     * 50% chance: Sets weather to clear and time to day, sends a funny VN message.
     * 50% chance: Allah is mad, sets weather to thunder and summons lightning on the player.
     */
    private static int executeSunshine(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        String playerName = player.getGameProfile().name();
        MinecraftServer server = context.getSource().getServer();
        
        try {
            boolean isMad = Math.random() < 0.5; // 50% chance
            
            if (isMad) {
                // Punishment!
                server.getCommands().performPrefixedCommand(
                    server.createCommandSourceStack().withSuppressedOutput(),
                    "weather thunder"
                );
                
                // Strike the player with lightning
                server.getCommands().performPrefixedCommand(
                    server.createCommandSourceStack().withSuppressedOutput(),
                    "execute at " + playerName + " run summon lightning_bolt ~ ~ ~"
                );
                
                // Funny Vietnamese angry broadcast
                MutableComponent msg = Component.literal("[Thời Tiết] ")
                    .withStyle(ChatFormatting.RED)
                    .append(Component.literal("Trời phạt! Dám gọi bão gọi mưa à? Sét đánh vỡ thớt " + playerName + " nhé! ⚡")
                        .withStyle(ChatFormatting.DARK_RED));
                        
                server.getPlayerList().broadcastSystemMessage(msg, false);
                
            } else {
                // Sunshine!
                server.getCommands().performPrefixedCommand(
                    server.createCommandSourceStack().withSuppressedOutput(),
                    "time set day"
                );
                server.getCommands().performPrefixedCommand(
                    server.createCommandSourceStack().withSuppressedOutput(),
                    "weather clear"
                );
                
                // Funny Vietnamese happy broadcast
                MutableComponent msg = Component.literal("[Thời Tiết] ")
                    .withStyle(ChatFormatting.YELLOW)
                    .append(Component.literal("Bùm! Phép thuật Winx Enchantix! Nắng to vỡ đầu rồi nhé các đạo hữu ơi! 🌞")
                        .withStyle(ChatFormatting.GOLD));
                        
                server.getPlayerList().broadcastSystemMessage(msg, false);
            }
            return 1;
        } catch (Exception e) {
            LOGGER.error("Failed to execute sunshine", e);
            return 0;
        }
    }
}
