package com.deadlyartist.jpa.entity;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class SettingsComponent implements Component, ServerTickingComponent, AutoSyncedComponent {
    public final Player player;
    public boolean engineDisabled = false;
    public boolean hoverDisabled = false;

    public SettingsComponent(Player player) {
        this.player = player;
    }

    @Override
    public void serverTick() {
        // Not needed for now
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        engineDisabled = tag.getBoolean("engineDisabled");
        hoverDisabled = tag.getBoolean("hoverDisabled");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putBoolean("engineDisabled", engineDisabled);
        tag.putBoolean("hoverDisabled", hoverDisabled);
    }
}
