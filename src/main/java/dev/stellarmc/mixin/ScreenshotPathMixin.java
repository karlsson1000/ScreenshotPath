package dev.stellarmc.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.stellarmc.config.ScreenshotPathConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class ScreenshotPathMixin {

    @Inject(
            method = "handleGlobalKeyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Screenshot;grab(Lnet/minecraft/client/Minecraft;Z)V"
            ),
            cancellable = true
    )
    private void screenshotPath$interceptScreenshot(
            InputConstants.Key key,
            boolean controlDown,
            CallbackInfoReturnable<Boolean> cir
    ) {
        Minecraft mc = (Minecraft)(Object)this;
        Screenshot.grab(
                ScreenshotPathConfig.getScreenshotPath().toFile(),
                mc.gameRenderer.mainRenderTarget(),
                msg -> mc.execute(() -> mc.showDebugChat(msg))
        );
        cir.setReturnValue(true);
    }
}