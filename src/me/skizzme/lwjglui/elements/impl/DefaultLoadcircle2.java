package me.skizzme.lwjglui.elements.impl;

import me.skizzme.lwjglui.elements.Element;
import me.skizzme.lwjglui.util.Animation;
import me.skizzme.lwjglui.util.GlTransformation;
import me.skizzme.lwjglui.util.Render;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;

public class DefaultLoadcircle2 extends Element {
    private Animation expand = new Animation(0, 0, 360);
    private Animation rotation = new Animation(0, 0, 360);
    private Color color;

    public DefaultLoadcircle2(int color, int x, int y, int radius) {
        super(x, y, radius, radius);
        this.color = new Color(color);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (expand.getValue() >= 180) {
            expand.animateBiLinear(360, 2, 0.8);
        } else {
            expand.animationInverseBiLinear(180, 7, 0.6);
        }
        if (expand.getValue() == 360) {
            expand.setValue(23);
        }
        if (rotation.getValue() == 360) {
            rotation.setValue(0);
        }
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(3);
        glBegin(GL_LINE_STRIP);
        glEnable(GL_ALPHA8);
        int completion = 290;
        for (double i = 0; i <= completion; i += 6) {
            if (i > 360) {
                break;
            }
            double theta = i * Math.PI / 180;
            glColor4d(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, (i/completion));
            glVertex2d(width * Math.cos(theta) + x, width * Math.sin(theta) + y);
        }
        glEnd();
        glColor4d(1,1,1,1);
        glDisable(GL_LINE_SMOOTH);
    }
}
