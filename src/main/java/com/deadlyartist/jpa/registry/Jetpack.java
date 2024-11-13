package com.deadlyartist.jpa.registry;

import com.deadlyartist.jpa.JPA;
import com.deadlyartist.jpa.item.JetpackItem;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class Jetpack {
    public String name;
    public int tier;
    public int color;
    public int armorPoints;
    public int enchantablilty;
    public String craftingMaterialString;
    private Ingredient craftingMaterial;
    public Supplier<JetpackItem> item;
    public boolean creative = false;
    public boolean disabled = false;
    public Rarity rarity = Rarity.COMMON;
    public double speedVert;
    public double accelVert;
    public double speedSide;
    public double speedHover;
    public double speedHoverSlow;
    public double sprintSpeed;
    
    public Jetpack(String name, int tier, int color, int armorPoints, int enchantability, String craftingMaterialString) {
        this.name = name.toLowerCase().replace(" ", "_");
        this.tier = tier;
        this.color = color;
        this.armorPoints = armorPoints;
        this.enchantablilty = enchantability;
        this.craftingMaterialString = craftingMaterialString;
        this.item = Suppliers.memoize(() -> new JetpackItem(this, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    }
    
    public Jetpack setStats(double speedVert, double accelVert, double speedSide, double speedHover, double speedHoverSlow, double sprintSpeed) {
        this.speedVert = speedVert;
        this.accelVert = accelVert;
        this.speedSide = speedSide;
        this.speedHover = speedHover;
        this.speedHoverSlow = speedHoverSlow;
        this.sprintSpeed = sprintSpeed;
        
        return this;
    }
    
    public Jetpack setCreative() {
        this.creative = true;
        this.tier = -1;
        this.rarity = Rarity.EPIC;
        
        return this;
    }
    
    public Jetpack setCreative(boolean set) {
        if (set) this.setCreative();
        return this;
    }
    
    public Jetpack setDisabled() {
        this.disabled = true;
        return this;
    }
    
    public Jetpack setDisabled(boolean set) {
        if (set) this.setDisabled();
        return this;
    }
    
    public Jetpack setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }
    
    public int getTier() {
        return this.tier;
    }
    
    public Ingredient getCraftingMaterial() {
        if (this.craftingMaterial == null || this.craftingMaterial.isEmpty()) {
            this.craftingMaterial = Ingredient.EMPTY;
            try {
                if (!this.craftingMaterialString.equalsIgnoreCase("null")) {
                    String[] parts = craftingMaterialString.split(":");
                    if (parts.length >= 3 && this.craftingMaterialString.startsWith("tag:")) {
                        TagKey<Item> tag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(parts[1], parts[2]));
                        if (tag != null)
                            this.craftingMaterial = Ingredient.of(tag);
                    } else if (parts.length >= 2) {
                        Item item = Registry.ITEM.get(new ResourceLocation(parts[0], parts[1]));
                        if (item != null)
                            this.craftingMaterial = Ingredient.of(item);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return this.craftingMaterial;
    }
}
