package com.deadlyartist.jpa.network;

import com.deadlyartist.jpa.JPA;
import com.deadlyartist.jpa.entity.JComponents;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class NetworkHandler {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(JPA.MOD_ID, "network");
    public static int counter = 0;
    public static int UPDATE_INPUT = register();
    public static int TOGGLE_ENGINE = register();
    public static int TOGGLE_HOVER = register();
    
    public static void onCommonSetup() {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (server, player, handler, buf, responseSender) -> {
            int id = buf.readInt();
            var settings = JComponents.SETTINGS.get(player);
            var sync = false;
            if (id == UPDATE_INPUT) {
                UpdateInputMessage.onMessage(UpdateInputMessage.read(buf), server, player);
            } else if (id == TOGGLE_ENGINE) {
                sync = true;
                settings.engineDisabled = !settings.engineDisabled;
            } else if (id == TOGGLE_HOVER) {
                sync = true;
                settings.hoverDisabled = !settings.hoverDisabled;
            }

            if (sync) JComponents.SETTINGS.sync(player);
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendToServer(int message) {
        sendToServer(message, buf -> {});
    }
    
    @Environment(EnvType.CLIENT)
    public static void sendToServer(int message, Consumer<FriendlyByteBuf> bufferWriter) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(message);
        bufferWriter.accept(buf);
        ClientPlayNetworking.send(PACKET_ID, buf);
    }

    @Environment(EnvType.CLIENT)
    public static void sendToServer(UpdateInputMessage message) {
        sendToServer(UPDATE_INPUT, buf -> UpdateInputMessage.write(message, buf));
    }

    public static int register() {
        counter++;
        return counter - 1;
    }
}
