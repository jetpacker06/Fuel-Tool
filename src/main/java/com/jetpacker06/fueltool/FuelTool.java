package com.jetpacker06.fueltool;

import com.google.gson.Gson;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Mod("fueltool")
public class FuelTool {
    public static final String MOD_ID = "fueltool";
    public static final Logger LOGGER = LogManager.getLogger();
    public static Map<String, Double> fuelsMap = createFuelsMap();
    public static void log(Object message) {FuelTool.LOGGER.info(message);}
    public FuelTool() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    public static Map<String, Double> createFuelsMap() {
        try {
            File listJson = new File(FMLPaths.GAMEDIR.get() + "/config/custom_fuels.json");
            if (!listJson.exists()) {
                listJson.createNewFile();
                FileWriter writer = new FileWriter(listJson);
                writer.write("{\n\"block.minecraft.grass_block\": 1600\n}");
                writer.close();
                log("Created custom_fuels.json");
            }
            else {log("Reading custom_fuels.json...");}
            Scanner reader = new Scanner(listJson);
            String rawText = "";
            while (reader.hasNextLine()) {
                rawText = rawText + reader.nextLine();
            }
            if (rawText.equals("")) rawText = "{}";

            Map<String, Double> map = new Gson().fromJson(rawText, Map.class);
            for (Map.Entry<String, Double> entry : map.entrySet()) {

            }
            return map;
        } catch (IOException e) {
            log("An error occurred involving custom_fuels.json. Go to the config folder and delete custom_fuels.json, then rerun the game.");
        }
        return null;
    }
}