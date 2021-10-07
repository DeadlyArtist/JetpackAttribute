package com.blakebr0.ironjetpacks.network.message;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ToggleEngineMessage {
    public static ToggleEngineMessage read(PacketByteBuf buffer) {
        return new ToggleEngineMessage();
    }
    
    public static void write(ToggleEngineMessage message, PacketByteBuf buffer) {
        
    }
    
    public static void onMessage(ToggleEngineMessage message, MinecraftServer server, ServerPlayerEntity player) {
        server.execute(() -> {
            if (player != null) {
                ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
                Item item = stack.getItem();
                if (item instanceof JetpackItem) {
                    JetpackItem jetpack = (JetpackItem) item;
                    jetpack.toggleEngine(stack);
                }
            }
        });
    }
}
