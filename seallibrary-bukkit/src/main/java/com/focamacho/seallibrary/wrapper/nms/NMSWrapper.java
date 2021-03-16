package com.focamacho.seallibrary.wrapper.nms;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.logger.SealLogger;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public abstract class NMSWrapper {

    public static NMSWrapper nmsWrapper;

    static {
        try {
            nmsWrapper = (NMSWrapper) Class.forName(NMSWrapper.class.getName() + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1)).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
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
