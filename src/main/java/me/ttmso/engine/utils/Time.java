package me.ttmso.engine.utils;

public class Time {
    public static long timeStarted = System.nanoTime();

    public static float getTime() {
        return ((float)(System.nanoTime() - timeStarted) * 1E-9f);
    }
}
