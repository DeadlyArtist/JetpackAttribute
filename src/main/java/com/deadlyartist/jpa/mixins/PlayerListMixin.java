package com.deadlyartist.jpa.mixins;

import com.deadlyartist.jpa.handler.InputHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "remove", at = @At("HEAD"))
    private void remove(ServerPlayer serverPlayer, CallbackInfo ci) {
        InputHandler.onLogout(serverPlayer);
    }
}
