package com.deadlyartist.jpa.handler;

import com.deadlyartist.jpa.JPA;
import com.deadlyartist.jpa.client.util.HudHelper;
import com.deadlyartist.jpa.client.util.HudHelper.HudPos;
import com.deadlyartist.jpa.config.Configs;
import com.deadlyartist.jpa.item.JetpackItem;
import com.deadlyartist.jpa.util.JetpackUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class HudHandler {
    //private static final ResourceLocation HUD_TEXTURE = new ResourceLocation(JPA.MOD_ID, "textures/gui/hud.png");
    
    public static void onRenderGameOverlay(PoseStack matrices, float delta) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            if (Configs.INSTANCE.enableHud && (Configs.INSTANCE.showHudOverChat || !(mc.screen instanceof ChatScreen)) && !mc.options.hideGui && !mc.options.renderDebug) {
                if (JetpackUtils.hasJetpackEquipped(mc.player)) {
                    HudPos pos = HudHelper.getHudPos();
                    if (pos != null) {
                        String engine = ChatFormatting.GRAY + "E: " + HudHelper.getOn(JetpackUtils.isEngineOn(mc.player));
                        String hover = ChatFormatting.GRAY + "H: " + HudHelper.getOn(JetpackUtils.isHovering(mc.player));
                        
                        if (pos.side == 1) {
                            mc.font.drawShadow(matrices, engine, pos.x - 8 - mc.font.width(engine), pos.y + 4, 16383998);
                            mc.font.drawShadow(matrices, hover, pos.x - 8 - mc.font.width(hover), pos.y + 14, 16383998);
                        } else {
                            mc.font.drawShadow(matrices, engine, pos.x + 6, pos.y + 4, 16383998);
                            mc.font.drawShadow(matrices, hover, pos.x + 6, pos.y + 14, 16383998);
                        }
    
                        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
                    }
                }
            }
        }
    }
}
