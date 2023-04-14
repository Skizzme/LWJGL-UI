package me.skizzme.lwjglui.fonts;

import me.skizzme.lwjglui.Window;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.sun.xml.internal.ws.client.ContentNegotiation.none;

public class FontUtil {
    public TTFRenderer hud = null;
    private static TTFFontRenderer defaultFont;
    private static final HashMap<String, TTFFontRenderer> fonts = new HashMap<>();
    private static final HashSet<String> loadingFonts = new HashSet<>();

    public static void loadDefaultFont() {
        defaultFont = createFont("/me/skizzme/lwjglui/assets/Comfortaa-Regular.ttf", 20);
    }

    public static TTFFontRenderer createFont(String fontPath, int size) {
        if (loadingFonts.contains(fontPath + size)) {
            return null;
        }
        try {
            loadingFonts.add(fontPath + size);

            int oldFps = Window.window().fps;
            Window.window().fps = 60;

            InputStream istream = FontUtil.class.getResourceAsStream(fontPath);
            Font myFont = Font.createFont(Font.PLAIN, istream);
            myFont = myFont.deriveFont(Font.PLAIN, size);
            TTFFontRenderer fontr = new TTFFontRenderer(myFont);

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
