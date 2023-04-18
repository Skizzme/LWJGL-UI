package me.skizzme.lwjglui.elements.impl;

import me.skizzme.lwjglui.elements.Element;
import me.skizzme.lwjglui.util.Animation;
import me.skizzme.lwjglui.util.Render;

public class DefaultLoadbar extends Element {
    private Animation progress;
    private double targetProgress;
    private int color, backgroundColor;

    public DefaultLoadbar(double progress, int color, int backgroundColor, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.progress = new Animation(progress, 0, 100);
        this.color = color;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        progress.animateBiLinear(targetProgress, 1, 0.5);
//        System.out.println(progress.getValue());
        Render.drawRect(x, y, x+width, y+height, color);
        Render.drawRect(x+0.5, y+0.5, x+width-0.5, y+height-0.5, backgroundColor);
        Render.drawRect(x, y, x+((width)*progress.getPercent()), y+height, color);
    }

    public void update(double progress) {
        targetProgress = Math.max(Math.min(progress, 100), 0);
    }
}
