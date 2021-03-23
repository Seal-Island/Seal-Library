package com.focamacho.seallibrary.wrapper.nms;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.logger.SealLogger;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public abstract class NMSWrapper {

    public static NMSWrapper nmsWrapper;

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

        Class<?>[] versions = {
                NMSWrapper1_12_R1.class,
                NMSWrapper1_16_R3.class
        };

        for(Class<?> v : versions) {
            if(v.getSimpleName().substring(10).equalsIgnoreCase(version)) {
                try {
                    nmsWrapper = (NMSWrapper) v.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        if(nmsWrapper == null) {
            SealLogger.error("A sua versão de servidor não é compatível com a Seal Library.");
            SealLogger.error("Muitas coisas não irão funcionar corretamente.");
        }
    }

    public abstract ItemStack getStackFromString(String id);

    public abstract String getDataFromStack(ItemStack stack);

    public abstract ItemStack setDataToStack(ItemStack stack, String data);

    public abstract String stackToJson(ISealStack stack);

    public abstract String getItemRegistryName(ItemStack stack);

}
