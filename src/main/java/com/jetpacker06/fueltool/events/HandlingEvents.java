package com.jetpacker06.fueltool.events;

import com.jetpacker06.fueltool.FuelTool;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings({"unused", "unchecked"})
@Mod.EventBusSubscriber(modid = FuelTool.MOD_ID)
public class HandlingEvents {
    public static void log(Object message) {FuelTool.LOGGER.info(message);}
    @SubscribeEvent
    public static void addFuels(FurnaceFuelBurnTimeEvent event) {
        String[] list = FuelTool.fuelsMap.keySet().toArray(new String[0]);
        for(int i = 0; i < list.length; i++) {
            if (list[i].toLowerCase().startsWith(event.getItemStack().getDescriptionId().toLowerCase())) {
                double value = FuelTool.fuelsMap.get(event.getItemStack().getDescriptionId());
                event.setBurnTime((int) Math.round(FuelTool.fuelsMap.get(event.getItemStack().getDescriptionId())));
                log("Set burn time of " + event.getItemStack().getDescriptionId() + "to" + FuelTool.fuelsMap.get(event.getItemStack().getDescriptionId()));
                break;
            }
        }
    }
}