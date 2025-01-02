package com.jetpacker06.fueltool;

import com.google.gson.Gson;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
                writer.write("{\n   \"item.minecraft.coal\": 1600\n}");
                writer.close();
                log("Created custom_fuels.json");
            }
            else {
                log("Reading custom_fuels.json...");
            }
            Scanner reader = new Scanner(listJson);
            String rawText = "";
            while (reader.hasNextLine()) {
                rawText = rawText + reader.nextLine();
            }
            if (rawText.equals("")) {
                rawText = "{}";
            }
            Map<String, Double> map = new Gson().fromJson(rawText, Map.class);
            return map;
        } catch (IOException e) {
            log("An error occurred involving custom_fuels.json. Go to the config folder and delete custom_fuels.json, then rerun the game.");
        }
        return null;
    }

    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class HandlingEvents {
        public static void log(Object message) {
            LOGGER.info(message);}
        @SubscribeEvent
        public static void addFuels(FurnaceFuelBurnTimeEvent event) {
            String[] list = fuelsMap.keySet().toArray(new String[0]);
            for(int i = 0; i < list.length; i++) {
                if (list[i].toLowerCase().startsWith(event.getItemStack().getDescriptionId().toLowerCase())) {
                    double value = fuelsMap.get(event.getItemStack().getDescriptionId());
                    event.setBurnTime((int) Math.round(fuelsMap.get(event.getItemStack().getDescriptionId())));
                    log("Set burn time of " + event.getItemStack().getDescriptionId() + "to" + fuelsMap.get(event.getItemStack().getDescriptionId()));
                    break;
                }
            }
        }
    }
}