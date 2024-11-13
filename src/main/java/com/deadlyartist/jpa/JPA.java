package com.deadlyartist.jpa;

import com.deadlyartist.jpa.entity.attribute.JEntityAttributes;
import com.deadlyartist.jpa.handler.InputHandler;
import com.deadlyartist.jpa.item.JetpackItem;
import com.deadlyartist.jpa.item.ModItems;
import com.deadlyartist.jpa.network.NetworkHandler;
import com.deadlyartist.jpa.sound.ModSounds;
import com.deadlyartist.jpa.util.AttributeModifierUtils;
import com.deadlyartist.jpa.util.SlotUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class JPA implements ModInitializer {
    public static final String MOD_ID = "jetpackattribute";
    public static final String NAME = "Jetpack Attribute";
    
    @Override
    public void onInitialize() {
        ModItems.register();
        ModSounds.register();
        
        NetworkHandler.onCommonSetup();
        
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            try {
                Class.forName("com.deadlyartist.jpa.client.JPAClient").getDeclaredMethod("onInitializeClient").invoke(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> InputHandler.clear());

        ModifyItemAttributeModifiersCallback.EVENT.register((stack, slot, attributeModifiers) -> {
            if (!SlotUtils.isEquipped(stack, slot)) return;

            if (stack.getItem() instanceof JetpackItem) {
                attributeModifiers.put(JEntityAttributes.JETPACK, AttributeModifierUtils.increment("jetpack_item"));
            }
        });
    }
}
