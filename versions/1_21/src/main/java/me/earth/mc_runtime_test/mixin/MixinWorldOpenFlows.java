package me.earth.mc_runtime_test.mixin;

import com.mojang.serialization.Lifecycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldOpenFlows.class, priority = 1001)
public class MixinWorldOpenFlows {

    @Inject(method = "confirmWorldCreation", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/mojang/serialization/Lifecycle;experimental()Lcom/mojang/serialization/Lifecycle;"), cancellable = true)
    private static void confirmWorldCreation(Minecraft minecraft, CreateWorldScreen createWorldScreen, Lifecycle lifecycle, Runnable runnable, boolean bl, CallbackInfo ci) {
        mc_runtime_test_mod$bypassExperimentalScreen(runnable, ci);
    }

    /**
     * Logic to skip experimental settings warning
     * @param loadWorld the class to load the world
     * @param ci callback info
     */
    @Unique
    private static void mc_runtime_test_mod$bypassExperimentalScreen(Runnable loadWorld, CallbackInfo ci) {
        loadWorld.run();
        ci.cancel();
    }
}
