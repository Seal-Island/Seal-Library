package com.focamacho.seallibrary.forge;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.wrapper.nms.NMSWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;

public class ForgeUtilsBukkit implements IForgeUtils {

    @Override
    public Object getForgeStack(Object item) {
        org.bukkit.inventory.ItemStack stack = (org.bukkit.inventory.ItemStack) item;
        String registryName = NMSWrapper.nmsWrapper.getItemRegistryName(stack);
        if(registryName.isEmpty()) return new ItemStack(Items.AIR);

        net.minecraft.item.Item forgeItem = net.minecraft.item.Item.getByNameOrId(registryName);
        if(forgeItem == null) return new ItemStack(Items.AIR);

        ItemStack forgeStack = new ItemStack(forgeItem, stack.getAmount(), stack.getDurability());

        String nbt = NMSWrapper.nmsWrapper.getDataFromStack(stack);
        if(!nbt.isEmpty()) {
            try {
                forgeStack.setTagCompound(JsonToNBT.getTagFromJson(nbt));
            } catch (NBTException ignored) {}
        }

        return forgeStack;
    }

    @Override
    public ISealStack getServerStack(Object item) {
        net.minecraft.item.Item it = ((ItemStack) item).getItem();
        if(it.getRegistryName() == null) return SealStack.get("");

        org.bukkit.inventory.ItemStack stack = NMSWrapper.nmsWrapper.getStackFromString(it.getRegistryName().toString() + (((ItemStack) item).getMetadata() > 0 ? ":" + ((ItemStack) item).getMetadata() : ""));

        ISealStack sealStack = SealStack.get(stack);
        net.minecraft.nbt.NBTTagCompound itemTag = ((ItemStack) item).getTagCompound();

        if(itemTag != null) {
            sealStack.setData(itemTag.toString());
        }

        return sealStack;
    }

}
