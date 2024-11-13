package com.deadlyartist.jpa.item;

import com.deadlyartist.jpa.JPA;
import com.deadlyartist.jpa.config.Jetpacks;
import com.deadlyartist.jpa.registry.Jetpack;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {
    public static void register() {
        Registry<Item> registry = Registry.ITEM;
        Jetpacks.loadJsons();
        var jetpack = Jetpacks.DEFAULT;
        Registry.register(registry, new ResourceLocation(JPA.MOD_ID, jetpack.name + "_jetpack"), jetpack.item.get());
    }
}
