package me.skizzme.lwjglui.fonts;

import me.skizzme.lwjglui.Window;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;

public class FontUtil {
    public TTFRenderer hud = null;
    private static TTFFontRenderer defaultFont;
    private static final HashMap<String, TTFFontRenderer> fonts = new HashMap<>();

    public static void loadDefaultFont() {
        defaultFont = createFont("/me/skizzme/lwjglui/assets/Comfortaa-Regular.ttf", 20);
    }

    public static TTFFontRenderer createFont(String fontPath, int size) {
        try {
            int oldFps = Window.window().fps;
            Window.window().fps = 20;
            InputStream istream = FontUtil.class.getResourceAsStream(fontPath);
            System.out.println(istream);
            Font myFont = Font.createFont(Font.PLAIN, istream);
            myFont = myFont.deriveFont(Font.PLAIN, size);
            TTFFontRenderer fontr = new TTFFontRenderer(myFont);
//            System.out.print(fontPath.split("/")[0].replace(".ttf", ""));
            fonts.put(fontPath + size, fontr);
            System.out.println("Loaded " + fontPath + " with size " + size);
            Window.window().fps = oldFps;
            return fontr;
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return defaultFont;
    }

    public static TTFFontRenderer getDefaultFont() {
        return defaultFont;
    }
    public static TTFFontRenderer getFont(String fontPath, int size) {
        if (!fonts.containsKey(fontPath+size)) {
            createFont(fontPath, size);
        }
        return fonts.getOrDefault(fontPath + size, defaultFont);
    }

    public void setup() {
        this.hud = new TTFRenderer("Arial", 0, 18);
    }
}
