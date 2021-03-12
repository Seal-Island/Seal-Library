package com.focamacho.seallibrary;

public class SealLibrary {

    private static Object instance;

    public static void init(Object instance) {
        SealLibrary.instance = instance;
    }

    public static void stop() {}

    public static void reload() {}

}
