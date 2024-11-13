package com.deadlyartist.jpa.item;

import com.deadlyartist.jpa.config.Configs;
import com.deadlyartist.jpa.handler.InputHandler;
import com.deadlyartist.jpa.lib.ModTooltips;
import com.deadlyartist.jpa.mixins.ServerPlayNetworkHandlerAccessor;
import com.deadlyartist.jpa.registry.Jetpack;
import com.deadlyartist.jpa.util.JetpackUtils;
import com.deadlyartist.jpa.util.StringUtils;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class JetpackItem extends DyeableArmorItem implements Colored, DyeableLeatherItem {
    private final Jetpack jetpack;
    
    public JetpackItem(Jetpack jetpack, Properties settings) {
        super(JetpackUtils.makeArmorMaterial(jetpack), EquipmentSlot.CHEST, settings.durability(0).rarity(jetpack.rarity));
        this.jetpack = jetpack;
    }
    
    @Override
    public Component getName(ItemStack stack) {
        String name = StringUtils.toNormalCase(jetpack.name);
        return Component.translatable("item.jetpackattribute.jetpack", name);
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return Configs.INSTANCE.enchantableJetpacks && this.jetpack.enchantablilty > 0;
    }
    
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag advanced) {
        Component tier = ModTooltips.TIER.args(this.jetpack.creative ? "Creative" : this.jetpack.tier).withStyle(this.jetpack.rarity.color);
        Component engine = ModTooltips.ENGINE.color(JetpackUtils.isEngineOn(Minecraft.getInstance().player) ? ChatFormatting.GREEN : ChatFormatting.RED);
        Component hover = ModTooltips.HOVER.color(JetpackUtils.isHovering(Minecraft.getInstance().player) ? ChatFormatting.GREEN : ChatFormatting.RED);
        
        tooltip.add(ModTooltips.STATE_TOOLTIP_LAYOUT.args(tier, engine, hover));
        
        if (Configs.INSTANCE.enableAdvancedInfoTooltips) {
            tooltip.add(Component.literal(""));
            if (!Screen.hasShiftDown()) {
                tooltip.add(Component.translatable("tooltip.jetpackattribute.hold_shift_for_info"));
            } else {
                tooltip.add(ModTooltips.VERTICAL_SPEED.args(this.jetpack.speedVert));
                tooltip.add(ModTooltips.VERTICAL_ACCELERATION.args(this.jetpack.accelVert));
                tooltip.add(ModTooltips.HORIZONTAL_SPEED.args(this.jetpack.speedSide));
                tooltip.add(ModTooltips.HOVER_SPEED.args(this.jetpack.speedHoverSlow));
                tooltip.add(ModTooltips.DESCEND_SPEED.args(this.jetpack.speedHover));
                tooltip.add(ModTooltips.SPRINT_MODIFIER.args(this.jetpack.sprintSpeed));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public int getColorTint(int i) {
        return i == 1 ? this.jetpack.color : -1;
    }
    
    @Override
    public boolean hasCustomColor(ItemStack stack) {
        return true;
    }
    
    @Override
    public int getColor(ItemStack stack) {
        return this.jetpack.color;
    }
    
    @Override
    public void clearColor(ItemStack stack) {
        
    }
    
    @Override
    public void setColor(ItemStack stack, int color) {
        
    }
    
    public Jetpack getJetpack() {
        return this.jetpack;
    }

    private void fly(Player player, double y) {
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x(), y, motion.z());
    }
}
