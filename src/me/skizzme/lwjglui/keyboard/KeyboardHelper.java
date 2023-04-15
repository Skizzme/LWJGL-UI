package me.skizzme.lwjglui.keyboard;

import java.util.HashMap;

public class KeyboardHelper {

    private static HashMap<Integer, Boolean> pressedKeys = new HashMap<>();

    public static void key(int keyCode, boolean pressed) {
        pressedKeys.put(keyCode, pressed);
    }

    public static boolean isPressed(int keyCode) {
        return pressedKeys.getOrDefault(keyCode, false);
    }

    public static boolean ctrl() {
        return pressedKeys.getOrDefault(29, false);
    }

    public static boolean shift() {
        return pressedKeys.getOrDefault(42, false);
    }

    public static boolean alt() {
        return pressedKeys.getOrDefault(56, false);
    }

    public static boolean win() {
        return pressedKeys.getOrDefault(219, false);
    }

}
