package dev.stellarmc.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.stellarmc.config.ScreenshotPathConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.network.chat.Component;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class ScreenshotPathCommand {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommands.literal("screenshotpath")
                        .then(ClientCommands.argument("path", StringArgumentType.greedyString())
                                .executes(ctx -> {
                                    String input = StringArgumentType.getString(ctx, "path");
                                    try {
                                        Path resolved = Path.of(input).toAbsolutePath();
                                        ScreenshotPathConfig.setPath(resolved.toString());
                                        ctx.getSource().sendFeedback(Component.literal("Screenshot path set to: " + resolved));
                                    } catch (InvalidPathException e) {
                                        ctx.getSource().sendError(Component.literal("Invalid path: " + input));
                                    }
                                    return 1;
                                })))
        );
    }
}