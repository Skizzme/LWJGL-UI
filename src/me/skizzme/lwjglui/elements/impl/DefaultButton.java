package me.skizzme.lwjglui.elements.impl;

import me.skizzme.lwjglui.elements.Element;
import me.skizzme.lwjglui.util.Render;

import java.util.function.BiConsumer;

public class DefaultButton extends Element {

    private BiConsumer<Integer, Boolean> executor;
    private String name;
    private int color, hoverColor;

    public DefaultButton(String name, int x, int y, int width, int height, int color, int hoverColor, BiConsumer<Integer, Boolean> executor) {
        super(x, y, width, height);
        this.name = name;
        this.color = color;
        this.hoverColor = hoverColor;
        this.executor = executor;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (this.hovering(mouseX, mouseY)) {
            Render.drawRect(x, y, x+height, y+width, hoverColor);
        } else {
            Render.drawRect(x, y, x+height, y+width, color);
        }
    }

    @Override
    public void click(int button, boolean state, int mouseX, int mouseY) {
        executor.accept(button, state);
    }

}
