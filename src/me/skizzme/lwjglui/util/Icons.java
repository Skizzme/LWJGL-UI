package me.skizzme.lwjglui.util;

import me.skizzme.lwjglui.fonts.FontUtil;
import me.skizzme.lwjglui.fonts.TTFFontRenderer;

import java.util.HashMap;

public class Icons {

    private static HashMap<String, String> idMap = new HashMap<>();
    private static HashMap<Integer, TTFFontRenderer> sizeMap = new HashMap<>();
    private static TTFFontRenderer defaultIcons;

    public static void setup() {
        idMap.put("add_box", "A");
        idMap.put("back", "B");
        idMap.put("check", "C");
        idMap.put("cross", "D");
        idMap.put("minimize", "E");
        idMap.put("trash", "F");
        idMap.put("dropdown", "G");
        idMap.put("home", "H");
        idMap.put("expand", "I");
        idMap.put("search", "J");
        idMap.put("settings", "S");
        idMap.put("terminal", "L");
        idMap.put("undo", "M");

        defaultIcons = FontUtil.createFont("/me/skizzme/lwjglui/assets/icons.ttf", 25, true);
        sizeMap.put(25, defaultIcons);
    }

    public static void drawIcon(int size, String icon, double x, double y, int color) {
        getIcons(size).drawString(idMap.get(icon), (float) x, (float) y, color);
    }

    public static void drawCenteredIcon(int size, String icon, double x, double y, int color) {
        TTFFontRenderer fr = getIcons(size);
        String id = idMap.get(icon);
        fr.drawString(id, (float) x - (int) (fr.getWidth(id))/2f, (float) y - (int) (fr.getHeight(id))/2f, color);
    }

    public static TTFFontRenderer getIcons(int size) {
        if (!sizeMap.containsKey(size)) {
            sizeMap.put(size, FontUtil.createFont("/me/skizzme/lwjglui/assets/icons.ttf", size, false));
        }
        return sizeMap.getOrDefault(size, defaultIcons);
    }

}
