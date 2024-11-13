package com.deadlyartist.jpa.config;

import com.deadlyartist.jpa.config.json.Serializers;
import com.deadlyartist.jpa.registry.Jetpack;
import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Jetpacks {
    public static final Jetpack MECHANICAL = new Jetpack("MECHANICAL", 0, 5658198, 3, 15, "null").setStats(0.5, 0.1, 0.01, 0.16, 0.14, 1.2);

    public static Jetpack DEFAULT = MECHANICAL;

    public static void loadJsons() {
        File dir = FabricLoader.getInstance().getConfigDir().resolve("jetpack-attribute").toFile();
        if (!dir.exists()) {
            if (!dir.mkdirs()) return;
        }

        Gson gson = Serializers.initGson();
        var jetpack = MECHANICAL;
        File file = new File(dir, "jetpack.json");
        if (!file.exists()) {
            String json = gson.toJson(jetpack);
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(json);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileReader reader = new FileReader(file);
                jetpack = gson.fromJson(reader, Jetpack.class);
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jetpack == null) jetpack = MECHANICAL;
        }

        DEFAULT = jetpack;
    }
}
