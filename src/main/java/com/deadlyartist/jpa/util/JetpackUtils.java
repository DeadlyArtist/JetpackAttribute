package com.deadlyartist.jpa.util;


import com.deadlyartist.jpa.config.Jetpacks;
import com.deadlyartist.jpa.entity.JComponents;
import com.deadlyartist.jpa.entity.attribute.JEntityAttributes;
import com.deadlyartist.jpa.handler.InputHandler;
import com.deadlyartist.jpa.item.JetpackItem;
import com.deadlyartist.jpa.mixins.ServerPlayNetworkHandlerAccessor;
import com.deadlyartist.jpa.network.NetworkHandler;
import com.deadlyartist.jpa.registry.Jetpack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec3;

public class JetpackUtils {
    public static String getDefaultName(Jetpack jetpack) {
        String name = StringUtils.toNormalCase(jetpack.name);
        return name + " Jetpack";
    }

    public static String getDefaultName(JetpackItem jetpackItem) {
        return getDefaultName(jetpackItem.getJetpack());
    }

    public static boolean hasJetpackEquipped(Player player) {
        return player.getAttributeValue(JEntityAttributes.JETPACK) == 1;
    }

    public static boolean hasDefaultJetpack(Player player) {
        return player.getAttributeValue(JEntityAttributes.JETPACK) == 1;
    }

    public static boolean isEngineOn(Player player) {
        return !JComponents.SETTINGS.get(player).engineDisabled;
    }

    @Environment(EnvType.CLIENT)
    public static void toggleEngine() {
        NetworkHandler.sendToServer(NetworkHandler.TOGGLE_ENGINE);
    }

    public static boolean isHovering(Player player) {
        return !JComponents.SETTINGS.get(player).hoverDisabled;
    }

    @Environment(EnvType.CLIENT)
    public static void toggleHover() {
        NetworkHandler.sendToServer(NetworkHandler.TOGGLE_HOVER);
    }

    public static void fly(Player player, double y) {
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x, y, motion.z);
    }

    public static boolean isFlying(Player player) {
        if (!hasJetpackEquipped(player)) return false;

        if (isEngineOn(player)) {
            if (isHovering(player)) {
                return !player.isOnGround();
            } else {
                return InputHandler.isHoldingUp(player);
            }
        }

        return false;
    }

    public static void tickJetpack(Player player) {
        if (!hasJetpackEquipped(player) || !isEngineOn(player)) return;

        boolean hover = isHovering(player);
        if (InputHandler.isHoldingUp(player) || hover && !player.isOnGround()) {
            Jetpack info = Jetpacks.DEFAULT;
            double hoverSpeed = InputHandler.isHoldingDown(player) ? info.speedHover : info.speedHoverSlow;
            double currentAccel = info.accelVert * (player.getDeltaMovement().y < 0.3 ? 2.5 : 1.0);
            double currentSpeedVertical = info.speedVert * (player.isInWater() ? 0.4 : 1.0);
            boolean creative = info.creative;

            double motionY = player.getDeltaMovement().y;
            if (InputHandler.isHoldingUp(player)) {
                if (!hover) {
                    fly(player, Math.min(motionY + currentAccel, currentSpeedVertical));
                } else if (InputHandler.isHoldingDown(player)) {
                    fly(player, Math.min(motionY + currentAccel, -info.speedHoverSlow));
                } else {
                    fly(player, Math.min(motionY + currentAccel, info.speedHover));
                }
            } else {
                fly(player, Math.min(motionY + currentAccel, -hoverSpeed));
            }

            float speedSideways = (float) (player.isCrouching() ? info.speedSide * 0.5 : info.speedSide);
            float speedForward = (float) (player.isSprinting() ? (double) speedSideways * info.sprintSpeed : (double) speedSideways);
            if (InputHandler.isHoldingForwards(player)) {
                player.moveRelative(speedForward, new Vec3(0.0, 0.0, 1.0));
            }

            if (InputHandler.isHoldingBackwards(player)) {
                player.moveRelative(-speedSideways * 0.8F, new Vec3(0.0, 0.0, 1.0));
            }

            if (InputHandler.isHoldingLeft(player)) {
                player.moveRelative(speedSideways, new Vec3(1.0, 0.0, 0.0));
            }

            if (InputHandler.isHoldingRight(player)) {
                player.moveRelative(-speedSideways, new Vec3(1.0, 0.0, 0.0));
            }

            if (!player.level.isClientSide()) {
                player.fallDistance = 0.0F;
                if (player instanceof ServerPlayer) {
                    ((ServerPlayNetworkHandlerAccessor) ((ServerPlayer) player).connection).setFloatingTicks(0);
                }
            }
        }

    }

    public static ArmorMaterial makeArmorMaterial(Jetpack jetpack) {
        return new ArmorMaterial() {
            @Override
            public int getDurabilityForSlot(EquipmentSlot slot) {
                return 0;
            }

            @Override
            public int getDefenseForSlot(EquipmentSlot slot) {
                return jetpack.armorPoints;
            }

            @Override
            public int getEnchantmentValue() {
                return jetpack.enchantablilty;
            }

            @Override
            public SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_GENERIC;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }

            @Environment(EnvType.CLIENT)
            @Override
            public String getName() {
                return "jpa:jetpack";
            }

            @Override
            public float getToughness() {
                return 0;
            }

            @Override
            public float getKnockbackResistance() {
                return 0;
            }
        };
    }
}
