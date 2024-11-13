package com.deadlyartist.jpa.client;

import com.deadlyartist.jpa.JPA;
import com.deadlyartist.jpa.config.Jetpacks;
import com.deadlyartist.jpa.network.NetworkHandler;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ModelHandler {
    private static final Logger LOGGER = LogManager.getLogger(JPA.NAME);
    
    public static void onClientSetup() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelResourceLocation(new ResourceLocation(JPA.MOD_ID, "jetpack"), "inventory"));
        });
        ResourceLocation jetpack = new ResourceLocation(JPA.MOD_ID, "item/jetpack");
        Map<ModelResourceLocation, UnbakedModel> modelMap = Maps.newHashMap();
        ResourceLocation jetpackLocation = Registry.ITEM.getKey(Jetpacks.DEFAULT.item.get());
        ModelResourceLocation location = new ModelResourceLocation(jetpackLocation, "inventory");
        provideModel(modelMap, location, jetpack);
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> (modelIdentifier, modelProviderContext) -> {
            return modelMap.get(modelIdentifier);
        });
    }
    
    private static void provideModel(Map<ModelResourceLocation, UnbakedModel> modelMap, ModelResourceLocation modelIdentifier, ResourceLocation redirectedId) {
        modelMap.put(modelIdentifier, new UnbakedModel() {
            @Override
            public Collection<ResourceLocation> getDependencies() {
                return Collections.emptyList();
            }
            
            @Override
            public Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
                return Collections.emptyList();
            }
            
            @Override
            public BakedModel bake(ModelBakery loader, Function<Material, TextureAtlasSprite> textureGetter, ModelState rotationContainer, ResourceLocation modelId) {
                return loader.bake(redirectedId, rotationContainer);
            }
        });
    }
}
