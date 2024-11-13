package com.deadlyartist.jpa.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotUtils {
    public static boolean isArmorSlot(EquipmentSlot slot) {
        return slot.getType() == EquipmentSlot.Type.ARMOR;
    }

    public static boolean isHandSlot(EquipmentSlot slot) {
        return slot.getType() == EquipmentSlot.Type.HAND;
    }

    public static boolean isEquipped(ItemStack itemStack, EquipmentSlot slot) {
        return slot == LivingEntity.getEquipmentSlotForItem(itemStack);
    }
}
