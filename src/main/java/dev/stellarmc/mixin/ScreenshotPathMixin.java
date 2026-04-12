package dev.stellarmc.mixin;

import dev.stellarmc.ScreenshotPath;
import dev.stellarmc.config.ScreenshotPathConfig;
import net.minecraft.client.util.ScreenshotRecorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mixin(ScreenshotRecorder.class)
public class ScreenshotPathMixin {

    @ModifyArg(
            method = "saveScreenshot(Ljava/io/File;Lnet/minecraft/client/gl/Framebuffer;Ljava/util/function/Consumer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/ScreenshotRecorder;saveScreenshot(Ljava/io/File;Ljava/lang/String;Lnet/minecraft/client/gl/Framebuffer;ILjava/util/function/Consumer;)V"
            ),
            index = 0
    )
    private static File screenshotPath$redirectDirectory(File original) {
        Path customDir = ScreenshotPathConfig.getScreenshotPath();
        try {
            if (!Files.exists(customDir)) {
                Files.createDirectories(customDir);
            }
            return customDir.toFile();
        } catch (IOException e) {
            ScreenshotPath.LOGGER.error("[ScreenshotPath] Failed to redirect screenshot path, falling back to default.", e);
            return original;
        }
    }
}