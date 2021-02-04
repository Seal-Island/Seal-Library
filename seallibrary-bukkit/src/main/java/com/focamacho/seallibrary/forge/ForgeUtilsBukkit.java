package com.focamacho.seallibrary.forge;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.util.ItemStackUtilsBukkit;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

public class ForgeUtilsBukkit implements IForgeUtils {

    @Override
    public Object getForgeStack(Object item) {
        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) item);
        MinecraftKey registry = Item.REGISTRY.b(stack.getItem());
        if(registry == null) return new ItemStack(Items.AIR);

        net.minecraft.item.Item forgeItem = net.minecraft.item.Item.getByNameOrId(registry.getKey() + ":" + registry.b());
        if(forgeItem == null) return new ItemStack(Items.AIR);

        ItemStack forgeStack = new ItemStack(forgeItem, stack.getCount(), stack.getData());

        NBTTagCompound itemTag = stack.getTag();
        if(itemTag != null) {
            try {
                forgeStack.setTagCompound(JsonToNBT.getTagFromJson(itemTag.toString()));
            } catch (NBTException ignored) {}
        }

        return forgeStack;
    }

    @Override
    public ISealStack getServerStack(Object item) {
        net.minecraft.item.Item it = ((ItemStack) item).getItem();
        if(it.getRegistryName() == null) return SealStack.get("");

        org.bukkit.inventory.ItemStack stack = ItemStackUtilsBukkit.getStackFromID(it.getRegistryName().toString() + (((ItemStack) item).getMetadata() > 0 ? ":" + ((ItemStack) item).getMetadata() : ""));
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        net.minecraft.nbt.NBTTagCompound itemTag = ((ItemStack) item).getTagCompound();
        if(itemTag != null) {
            try {
                nmsStack.setTag(MojangsonParser.parse(itemTag.toString()));
            } catch (MojangsonParseException ignored) {}
        }

        return SealStack.get(CraftItemStack.asBukkitCopy(nmsStack));
    }

}
