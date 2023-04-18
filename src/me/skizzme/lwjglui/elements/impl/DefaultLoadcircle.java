package me.skizzme.lwjglui.elements.impl;

import me.skizzme.lwjglui.elements.Element;
import me.skizzme.lwjglui.util.Animation;
import me.skizzme.lwjglui.util.GlTransformation;
import me.skizzme.lwjglui.util.Render;

import static org.lwjgl.opengl.GL11.*;

public class DefaultLoadcircle extends Element {
    private Animation expand = new Animation(0, 0, 360);
    private Animation rotation = new Animation(0, 0, 360);

    public DefaultLoadcircle(int x, int y, int radius) {
        super(x, y, radius, radius);
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
        rotation.animateBiLinear(360, 15, 0.2);
        GlTransformation t1 = GlTransformation.rotationWithAxis(rotation.getValue(), 0, 0, 1, x, y);

        t1.apply();
        Render.drawCircle(0, 0, width, 5, 4, false, -1, (int) expand.getValue()+23);
        t1.remove();
    }
}
