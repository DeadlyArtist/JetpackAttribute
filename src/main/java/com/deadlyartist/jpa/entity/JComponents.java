package com.deadlyartist.jpa.entity;

import com.deadlyartist.jpa.JPA;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;

public class JComponents implements EntityComponentInitializer {
    // retrieving a type for my component or for a required dependency's
    // DONT FORGET TO ADD TO fabric.mod.json
    public static final ComponentKey<SettingsComponent> SETTINGS = register("SETTINGS", SettingsComponent.class);


    public static <T extends Component> ComponentKey<T> register(String id, Class<T> componentClass) {
        var key = ComponentRegistry.getOrCreate(new ResourceLocation(JPA.MOD_ID, id.toLowerCase()), componentClass);
        return key;
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(SETTINGS, SettingsComponent::new, RespawnCopyStrategy.ALWAYS_COPY);

    }
}