package com.deadlyartist.jpa.entity.attribute;

import com.deadlyartist.jpa.JPA;
import com.deadlyartist.jpa.util.StringUtils;
import net.fabricmc.fabric.mixin.object.builder.DefaultAttributeRegistryAccessor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JEntityAttributes {

    public static final Set<Attribute> data = new HashSet<>();

    public static final Attribute JETPACK = registerClampedEntityAttribute("JETPACK", 0.0, 0.0, 1.0);

    public static Attribute registerClampedEntityAttribute(String id, double fallback, double min, double max) {
        id = id.toLowerCase();
        return register(id, new RangedAttribute("attribute.name.generic." + id, fallback, min, max).setSyncable(true));
    }

    private static Attribute register(String id, Attribute attribute) {
        Attribute entityAttribute = Registry.register(Registry.ATTRIBUTE, new ResourceLocation(JPA.MOD_ID, id.toLowerCase()), attribute);
        data.add(entityAttribute);
        return entityAttribute;
    }
}
