package com.deadlyartist.jpa.config.json;

import com.deadlyartist.jpa.registry.Jetpack;
import com.google.gson.*;
import java.lang.reflect.Type;
import net.minecraft.world.item.Rarity;

public class Serializers {
    public static Gson initGson() {
        GsonBuilder gson = new GsonBuilder();
        
        gson.setPrettyPrinting();
        gson.serializeNulls();
        gson.disableHtmlEscaping();
        gson.registerTypeAdapter(JETPACK.getType(), JETPACK);
        
        return gson.create();
    }
    
    public static final BaseSerializer<Jetpack> JETPACK = new BaseSerializer<Jetpack>() {
        @Override
        public Jetpack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            
            String name = obj.get("name").getAsString();
            boolean disable = obj.get("disable").getAsBoolean();
            int tier = obj.get("tier").getAsInt();
            int color = Integer.parseInt(obj.get("color").getAsString(), 16);
            int armorPoints = obj.get("armorPoints").getAsInt();
            int enchantability = obj.get("enchantability").getAsInt();
            String craftingMaterialString = obj.get("craftingMaterial").getAsString();
            boolean creative = false;
            if (obj.has("creative"))
                creative = obj.get("creative").getAsBoolean();
            Rarity rarity = Rarity.COMMON;
            if (obj.has("rarity"))
                rarity = Rarity.values()[obj.get("rarity").getAsInt()];
            
            Jetpack jetpack = new Jetpack(name, tier, color, armorPoints, enchantability, craftingMaterialString).setRarity(rarity).setCreative(creative).setDisabled(disable);

            double speedVert = obj.get("speedVertical").getAsDouble();
            double accelVert = obj.get("accelVertical").getAsDouble();
            double speedSide = obj.get("speedSideways").getAsDouble();
            double speedHover = obj.get("speedHoverDescend").getAsDouble();
            double speedHoverSlow = obj.get("speedHover").getAsDouble();
            double sprintSpeed = obj.get("sprintSpeedMulti").getAsDouble();
            
            jetpack.setStats(speedVert, accelVert, speedSide, speedHover, speedHoverSlow, sprintSpeed);
            
            return jetpack;
        }
        
        @Override
        public JsonElement serialize(Jetpack src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            
            obj.addProperty("name", src.name);
            obj.addProperty("disable", src.disabled);
            obj.addProperty("tier", src.tier);
            obj.addProperty("color", Integer.toHexString(src.color));
            obj.addProperty("armorPoints", src.armorPoints);
            obj.addProperty("enchantability", src.enchantablilty);
            obj.addProperty("craftingMaterial", src.craftingMaterialString);
            if (src.creative)
                obj.addProperty("creative", true);
            obj.addProperty("rarity", src.rarity.ordinal());

            obj.addProperty("speedVertical", src.speedVert);
            obj.addProperty("accelVertical", src.accelVert);
            obj.addProperty("speedSideways", src.speedSide);
            obj.addProperty("speedHoverDescend", src.speedHover);
            obj.addProperty("speedHover", src.speedHoverSlow);
            obj.addProperty("sprintSpeedMulti", src.sprintSpeed);
            
            return obj;
        }
        
        @Override
        public Type getType() {
            return Jetpack.class;
        }
    };
    
    private static abstract class BaseSerializer<T> implements JsonSerializer<T>, JsonDeserializer<T> {
        public abstract Type getType();
    }
}
