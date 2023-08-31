package me.skizzme.lwjglui.util;

import com.sun.prism.paint.Color;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    public static HashMap<String, Texture> textures = new HashMap<>();

    public static void initMask() {
        glClearDepth(1.0f);
        glClear(GL_DEPTH_BUFFER_BIT);
        glColorMask(false, false, false, false);
        glDepthFunc(GL_LESS);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
    }

    public static void initMask(int depth) {
        glClearDepth(depth);
        glClear(GL_DEPTH_BUFFER_BIT);
        glColorMask(false, false, false, false);
        glDepthFunc(GL_LESS);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
    }

    public static void useMask() {
        glColorMask(true, true, true, true);
        glDepthMask(true);
        glDepthFunc(GL_EQUAL);
    }

    public static void disableMask() {
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
    }

    public static void glColor(int hex) {
        double alpha = (hex >> 24 & 255) / 255.0f;
        double red = (hex >> 16 & 255) / 255.0f;
        double green = (hex >> 8 & 255) / 255.0f;
        double blue = (hex & 255) / 255.0f;
        glColor4d(red, green, blue, alpha);
    }

    public static int hexWithAlpha(int hex, double alpha) {
        alpha = alpha/255;
        float red = ((float)(hex >> 16 & 255) / 255.0F);
        float green = ((float)(hex >> 8 & 255) / 255.0F);
        float blue = ((float)(hex & 255) / 255.0F);
        return new Color(red, green, blue, (float) alpha).getIntArgbPre();
    }

    public static void drawUnderline(double x, double y, double width, double fade) {
        glEnable(GL_BLEND);
        Render.drawHorizontalGradient(x, y, x+fade, y+1, 0x00cccccc, 0xffcccccc);
        Render.drawRect(x+fade, y, x+width-fade, y+1, 0xffcccccc);
        Render.drawHorizontalGradient(x+width-fade, y, x+width, y+1, 0xffcccccc, 0x00cccccc);
        glDisable(GL_BLEND);
    }

    public static void drawUnderline(double x, double y, double width, double fade, double red, double green, double blue, double alpha) {
        glEnable(GL_BLEND);
        int color1 = fromRGB(red, green, blue, alpha);
        int color2 = fromRGB(red, green, blue, 0);
        Render.drawHorizontalGradient(x, y, x+fade, y+1, color2, color1);
        Render.drawRect(x+fade, y, x+width-fade, y+1, color1);
        Render.drawHorizontalGradient(x+width-fade, y, x+width, y+1, color1, color2);
        glDisable(GL_BLEND);
    }

    public static void drawTexture(String asset, double left, double top, double right, double bottom, int tint) {
        try {
            if (textures.get(asset) == null) {
                InputStream stream = Render.class.getResourceAsStream(asset);
                if (stream == null) {
                    System.err.println("Couldn't load asset \"" + asset + "\"");
                    return;
                }
                textures.put(asset, BufferedImageUtil.getTexture(asset, ImageIO.read(stream)));
            }
            glColor(tint);
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
            glBindTexture(GL_TEXTURE_2D, textures.get(asset).getTextureID());
            glBegin(GL_QUADS);
            GL11.glTexCoord2f(0,0);
            glVertex2d(left, top);
            GL11.glTexCoord2f(1,0);
            glVertex2d(right, top);
            GL11.glTexCoord2f(1,1);
            glVertex2d(right, bottom);
            GL11.glTexCoord2f(0, 1);
            glVertex2d(left, bottom);
            glEnd();
            glColor4d(1,1,1,1);
            glDisable(GL_TEXTURE_2D);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void drawTexture(String asset, double left, double top, double right, double bottom) {
        drawTexture(asset, left, top, right, bottom, -1);
    }

    public static int fromRGB(double r, double g, double b, double a) {
        return ((clampRGB(a) & 0xFF) << 24) |
                ((clampRGB(r) & 0xFF) << 16) |
                ((clampRGB(g) & 0xFF) << 8)  |
                ((clampRGB(b) & 0xFF));
    }

    public static int clampRGB(double value) {
        return (int) Math.min(Math.max(value, 0), 255);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glColor(color);
        glBegin(GL_QUADS);
        glVertex2d(left, bottom);
        glVertex2d(right, bottom);
        glVertex2d(right, top);
        glVertex2d(left, top);
        glEnd();
        glDisable(GL_BLEND);
    }

    public static void drawRect(double left, double top, double right, double bottom, double red, double green, double blue, double alpha) {
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glColor4d(red/255d, green/255d, blue/255d, alpha/255d);
        glBegin(GL_QUADS);
        glVertex2d(left, bottom);
        glVertex2d(right, bottom);
        glVertex2d(right, top);
        glVertex2d(left, top);
        glEnd();
        glDisable(GL_BLEND);

    }

    public static void drawHorizontalGradient(double left, double top, double right, double bottom, int leftColor, int rightColor) {
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBegin(GL_QUADS);
        glColor(leftColor);
        glVertex2d(left, top);
        glVertex2d(left, bottom);
        glColor(rightColor);
        glVertex2d(right, bottom);
        glVertex2d(right, top);
        glEnd();
        glDisable(GL_BLEND);
    }

    public static void drawCircle(double x,double y, double radius, double increment, int lineWidth, boolean fill, int color, int completion) {
        if (fill) {
            drawRoundedRect(x-radius, y-radius, x+radius, y+radius, radius*2, color, increment);
        }
        else{
            glEnable(GL_LINE_SMOOTH);
            glLineWidth(lineWidth);
            glColor(color);
            glBegin(GL_LINE_STRIP);
            for (double i = 0; i <= completion; i += increment) {
                if (i > 360) {
                    break;
                }
                double theta = i * Math.PI / 180;
                glVertex2d(radius * Math.cos(theta) + x, radius * Math.sin(theta) + y);
            }
            glEnd();
            glColor4d(1,1,1,1);
            glDisable(GL_LINE_SMOOTH);
        }
    }

    public static void drawRoundedRect(double left, double top, double right, double bottom, double radius, int color) {
        drawRoundedRect(left, top, right, bottom, radius, color, 1);
    }

    public static void drawRoundedRect(double left, double top, double right, double bottom, double radius, int color, double increment) {
        glScaled(0.5D, 0.5D, 0.5D);
        left *= 2.0D;
        top *= 2.0D;
        right *= 2.0D;
        bottom *= 2.0D;
        glColor(color);
        glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        glBegin(GL_POLYGON);
        int i;
        for (i = 0; i <= 90; i += increment)
            glVertex2d(left + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, top + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 90; i <= 180; i += increment)
            glVertex2d(left + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, bottom - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        glVertex2d(right - radius,bottom);
        for (i = 0; i <= 90; i += increment)
            glVertex2d(right - radius + Math.sin(i * Math.PI / 180.0D) * radius, bottom - radius + Math.cos(i * Math.PI / 180.0D) * radius);
        for (i = 90; i <= 180; i += increment)
            glVertex2d(right - radius + Math.sin(i * Math.PI / 180.0D) * radius, top + radius + Math.cos(i * Math.PI / 180.0D) * radius);
        glEnd();
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        glDisable(GL_POLYGON_SMOOTH);
        glScaled(2.0D, 2.0D, 2.0D);
        glColor4d(1,1,1,1);
    }

}
