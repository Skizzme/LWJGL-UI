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
    private boolean expandState = false;
    private Animation rotation = new Animation(0, 0, 360);
    private Color color;

    public DefaultLoadcircle2(int color, int x, int y, int radius) {
        super(x, y, radius, radius);
        this.color = new Color(color);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        if (expandState) {
            expand.animateBiLinear(0, 5, 0.7);
            if (expand.getValue() == 0) {
                expandState = false;
            }
        } else {
            expand.animateLinear(360, 50);
//            if (expand.getValue() >= 180) {
//                expand.animateBiLinear(360, 0.8, 0.3);
//            } else {
//                expand.animationInverseBiLinear(180, 1, 0.3);
//            }
            if (expand.getValue() == 360) {
                expandState = true;
            }
        }
        if (rotation.getValue() == 360) {
            rotation.setValue(0);
        }
        rotation.animate(360, 4);
        GlTransformation t1 = GlTransformation.rotationWithAxis(rotation.getValue(), 0, 0, 1, x, y);

        t1.apply();
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(3);
        glEnable(GL_BLEND);
        glBegin(GL_LINE_STRIP);
        int completion = (int) expand.getValue()+23;
        for (double i = 360; i >= 360-completion; i -= 6) {
            if (i > 360) {
                break;
            }
            double theta = i * Math.PI / 180;
            glColor4d(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, (i/completion));
            double x = width * Math.cos(theta);
            double y = width * Math.sin(theta);
            glVertex2d(x, y);
        }
        glEnd();
        glColor4d(1,1,1,1);
        glDisable(GL_LINE_SMOOTH);
        t1.remove();
    }
}
