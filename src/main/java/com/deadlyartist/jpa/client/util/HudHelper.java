package com.deadlyartist.jpa.client.util;

import com.deadlyartist.jpa.config.Configs;
import com.deadlyartist.jpa.item.JetpackItem;
import com.deadlyartist.jpa.lib.ModTooltips;
import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class HudHelper {
    public static HudPos getHudPos() {
        Window window = Minecraft.getInstance().getWindow();
        int xOffset = Configs.INSTANCE.hudXOffset;
        int yOffset = Configs.INSTANCE.hudYOffset;
        int hudPosition = Configs.INSTANCE.hudPosition;
        
        switch (hudPosition) {
            case 0:
                return new HudPos(10 + xOffset, 30 + yOffset, 0);
            case 1:
                return new HudPos(10 + xOffset, window.getGuiScaledHeight() / 2 + yOffset, 0);
            case 2:
                return new HudPos(10 + xOffset, window.getGuiScaledHeight() - 30 + yOffset, 0);
            case 3:
                return new HudPos(window.getGuiScaledWidth() - 8 - xOffset, 30 + yOffset, 1);
            case 4:
                return new HudPos(window.getGuiScaledWidth() - 8 - xOffset, window.getGuiScaledHeight() / 2 + yOffset, 1);
            case 5:
                return new HudPos(window.getGuiScaledWidth() - 8 - xOffset, window.getGuiScaledHeight() - 30 + yOffset, 1);
        }
        
        return null;
    }
    
    public static String getOn(boolean on) {
        return on ? ModTooltips.ON.color(ChatFormatting.GREEN).getString() : ModTooltips.OFF.color(ChatFormatting.RED).getString();
    }
    
    public static class HudPos {
        public int x;
        public int y;
        public int side;
        
        public HudPos(int x, int y, int side) {
            this.x = x;
            this.y = y;
            this.side = side;
        }
    }
}
