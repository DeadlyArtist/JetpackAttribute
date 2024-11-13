package com.deadlyartist.jpa.handler;

import com.deadlyartist.jpa.config.Jetpacks;
import com.deadlyartist.jpa.item.Colored;
import com.deadlyartist.jpa.registry.Jetpack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.level.ItemLike;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ColorHandler {
    private static final List<ItemLike> COLORED_ITEMS = new ArrayList<>();
    
    public static void onClientSetup() {
        COLORED_ITEMS.add(Jetpacks.DEFAULT.item.get());
        
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            Colored item = (Colored) stack.getItem();
            return item.getColorTint(tintIndex);
        }, COLORED_ITEMS.toArray(new ItemLike[0]));
    }
}
