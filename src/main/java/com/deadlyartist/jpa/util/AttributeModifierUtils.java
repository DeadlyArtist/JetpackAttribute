package com.deadlyartist.jpa.util;

import com.deadlyartist.jpa.JPA;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;

public class AttributeModifierUtils {
    public static AttributeModifier of(String name, double value, AttributeModifier.Operation operation) {
        if (name.isEmpty()) return new AttributeModifier(name, value, operation);
        else return new AttributeModifier(UUIDUtils.of(name), name, value, operation);
    }

    public static AttributeModifier of(double value, AttributeModifier.Operation operation, boolean randomName) {
        return of(randomName ? String.join("___", List.of(StringUtils.random(), JPA.MOD_ID, String.valueOf(value), String.valueOf(operation.toValue()))) : "", value, operation);
    }

    public static AttributeModifier of(double value, AttributeModifier.Operation operation) {
        return of(value, operation, false);
    }

    public static AttributeModifier copyWithName(String name, AttributeModifier modifier) {
        return of(name, modifier.getAmount(), modifier.getOperation());
    }

    public static AttributeModifier add(double value) {
        return of(value, AttributeModifier.Operation.ADDITION);
    }

    public static AttributeModifier increment() {
        return add(1);
    }

    public static AttributeModifier add(String name, double value) {
        return of(name, value, AttributeModifier.Operation.ADDITION);
    }

    public static AttributeModifier increment(String name) {
        return add(name, 1);
    }
}
