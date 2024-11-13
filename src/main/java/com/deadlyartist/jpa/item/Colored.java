package com.deadlyartist.jpa.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface Colored {
    @Environment(EnvType.CLIENT)
    int getColorTint(int tintIndex);
}
