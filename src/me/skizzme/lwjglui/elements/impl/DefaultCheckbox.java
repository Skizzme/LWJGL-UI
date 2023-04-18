package me.skizzme.lwjglui.elements.impl;

import me.skizzme.lwjglui.elements.Element;
import me.skizzme.lwjglui.fonts.FontUtil;
import me.skizzme.lwjglui.fonts.TTFFontRenderer;
import me.skizzme.lwjglui.util.Render;

public class DefaultCheckbox extends Element {
    private String name;
    private float fontHeight;
    private int color, backgroundColor, textColor;
    private boolean state;

    public DefaultCheckbox(String name, int color, int backgroundColor, int textColor, boolean state, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.state = state;
        this.fontHeight = FontUtil.getDefaultFont().getHeight(name);
    }

    public boolean selected() {
        return state;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        Render.drawRect(x, y, x+width, y+height, color);
        Render.drawRect(x+0.5, y+0.5, x+width-0.5, y+height-0.5, backgroundColor);
        if (state) {
            Render.drawRect(x+1.5, y+1.5, x+width-1.5, y+height-1.5, color);
        }
        TTFFontRenderer tf = FontUtil.getDefaultFont();
        tf.drawString(name, x+width+3, y+height/2f-fontHeight/2f+1, textColor);
    }

    @Override
    public void clicked(int button, boolean state, int mouseX, int mouseY) {
        super.clicked(button, state, mouseX, mouseY);
        this.state = !this.state;
    }
}
