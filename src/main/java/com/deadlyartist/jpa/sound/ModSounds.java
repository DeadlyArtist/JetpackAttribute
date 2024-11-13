package com.deadlyartist.jpa.sound;

import com.deadlyartist.jpa.JPA;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final ResourceLocation JETPACK_ID = new ResourceLocation(JPA.MOD_ID, "jetpack");
    public static final Supplier<SoundEvent> JETPACK = Suppliers.memoize(() -> new SoundEvent(JETPACK_ID));
    
    public static void register() {
        Registry.register(Registry.SOUND_EVENT, JETPACK_ID, JETPACK.get());
    }
}
