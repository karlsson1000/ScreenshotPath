package dev.stellarmc.mixin;

import dev.stellarmc.config.ScreenshotPathConfig;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.io.File;

@Mixin(KeyboardHandler.class)
public class ScreenshotPathMixin {

    @ModifyArg(
            method = "keyPress(JILnet/minecraft/client/input/KeyEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Screenshot;grab(Ljava/io/File;Lcom/mojang/blaze3d/pipeline/RenderTarget;Ljava/util/function/Consumer;)V"
            ),
            index = 0
    )
    private File screenshotPath$redirectDir(File original) {
        return ScreenshotPathConfig.getScreenshotPath().toFile();
    }
}