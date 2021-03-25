package com.focamacho.seallibrary.nbt;

import com.focamacho.seallibrary.impl.Implementations;

@SuppressWarnings("unused")
public class SealNBT {

    public static ISealNBT create() {
        return Implementations.nbtGetter.create();
    }

    public static ISealNBT get(Object nbt) {
        return Implementations.nbtGetter.get(nbt);
    }

}
