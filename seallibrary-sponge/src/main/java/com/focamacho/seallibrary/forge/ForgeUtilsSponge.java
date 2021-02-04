package com.focamacho.seallibrary.forge;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

public class ForgeUtilsSponge implements IForgeUtils {

    @Override
    public Object getForgeStack(Object item) {
        return ItemStackUtil.toNative((org.spongepowered.api.item.inventory.ItemStack) item);
    }

    @Override
    public ISealStack getServerStack(Object item) {
        return SealStack.get(ItemStackUtil.fromNative((ItemStack) item));
    }

}
