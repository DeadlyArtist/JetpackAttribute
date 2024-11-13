package com.deadlyartist.jpa.mixins;

import com.deadlyartist.jpa.handler.InputHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(method = "triggerDimensionChangeTriggers", at = @At("HEAD"))
    private void changeDimension(ServerLevel serverLevel, CallbackInfo ci) {
        InputHandler.onLogout((ServerPlayer) (Object) this);
    }
}
