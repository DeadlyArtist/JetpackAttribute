package com.deadlyartist.jpa.handler;

import com.deadlyartist.jpa.JPA;
import com.deadlyartist.jpa.item.JetpackItem;
import com.deadlyartist.jpa.lib.ModTooltips;
import com.deadlyartist.jpa.network.NetworkHandler;
import com.deadlyartist.jpa.network.UpdateInputMessage;
import com.deadlyartist.jpa.util.JetpackUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyBindingsHandler {
    private static KeyMapping keyEngine;
    private static KeyMapping keyHover;
    private static KeyMapping keyDescend;
    
    private static boolean up = false;
    private static boolean down = false;
    private static boolean forwards = false;
    private static boolean backwards = false;
    private static boolean left = false;
    private static boolean right = false;
    
    public static void onClientSetup() {
        keyEngine = create("engine", GLFW.GLFW_KEY_V, JPA.NAME);
        keyHover = create("hover", GLFW.GLFW_KEY_G, JPA.NAME);
        keyDescend = create("descend", InputConstants.UNKNOWN.getValue(), JPA.NAME);
    }
    
    private static KeyMapping create(String id, int key, String category) {
        return KeyBindingHelper.registerKeyBinding(new KeyMapping("key." + JPA.MOD_ID + "." + id, InputConstants.Type.KEYSYM, key, category));
    }
    
    public static void onClientTick(Minecraft client) {
        handleInputs(client);
        updateInputs(client);
    }
    
    private static void handleInputs(Minecraft client) {
        Player player = client.player;
        if (player == null)
            return;
        
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        Item item = chest.getItem();
        
        if (item instanceof JetpackItem) {
            JetpackItem jetpack = (JetpackItem) item;
            boolean engineOn = JetpackUtils.isEngineOn(player);
            while (keyEngine.consumeClick()) {
                engineOn = !engineOn;
                NetworkHandler.sendToServer(NetworkHandler.TOGGLE_ENGINE);
                Component state = engineOn ? ModTooltips.ON.color(ChatFormatting.GREEN) : ModTooltips.OFF.color(ChatFormatting.RED);
                player.displayClientMessage(ModTooltips.TOGGLE_ENGINE.args(state), true);
            }

            boolean hoverOn = JetpackUtils.isEngineOn(player);
            while (keyHover.consumeClick()) {
                hoverOn = !hoverOn;
                NetworkHandler.sendToServer(NetworkHandler.TOGGLE_HOVER);
                Component state = hoverOn ? ModTooltips.ON.color(ChatFormatting.GREEN) : ModTooltips.OFF.color(ChatFormatting.RED);
                player.displayClientMessage(ModTooltips.TOGGLE_HOVER.args(state), true);
            }
        }
    }
    
    /*
     * Keyboard handling borrowed from Simply Jetpacks
     * https://github.com/Tomson124/SimplyJetpacks-2/blob/1.12/src/main/java/tonius/simplyjetpacks/client/handler/KeyTracker.java
     */
    public static void updateInputs(Minecraft client) {
        Options settings = client.options;
        
        if (client.getConnection() == null)
            return;
        
        boolean upNow = settings.keyJump.isDown();
        boolean downNow = keyDescend.isUnbound() ? settings.keyShift.isDown() : keyDescend.isDown();
        boolean forwardsNow = settings.keyUp.isDown();
        boolean backwardsNow = settings.keyDown.isDown();
        boolean leftNow = settings.keyLeft.isDown();
        boolean rightNow = settings.keyRight.isDown();
        
        if (upNow != up || downNow != down || forwardsNow != forwards || backwardsNow != backwards || leftNow != left || rightNow != right) {
            up = upNow;
            down = downNow;
            forwards = forwardsNow;
            backwards = backwardsNow;
            left = leftNow;
            right = rightNow;
            
            NetworkHandler.sendToServer(new UpdateInputMessage(upNow, downNow, forwardsNow, backwardsNow, leftNow, rightNow));
            InputHandler.update(client.player, upNow, downNow, forwardsNow, backwardsNow, leftNow, rightNow);
        }
    }
}
