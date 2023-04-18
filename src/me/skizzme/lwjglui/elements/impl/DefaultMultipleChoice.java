package me.skizzme.lwjglui.elements.impl;

import me.skizzme.lwjglui.elements.Element;
import me.skizzme.lwjglui.fonts.FontUtil;
import me.skizzme.lwjglui.fonts.TTFFontRenderer;
import me.skizzme.lwjglui.util.Render;

public class DefaultMultipleChoice extends Element {
    private String[] options;
    private float[] fontHeights;
    private int optionHeight;
    private int color, backgroundColor, textColor;
    private int selected = -1;

    public DefaultMultipleChoice(int color, int backgroundColor, int textColor, int selected, int x, int y, int width, int height, String... options) {
        super(x, y, width, (height+3)*options.length);
        this.options = options;
        this.optionHeight = height;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.selected = selected;
        this.fontHeights = new float[options.length];
        for (int i = 0; i < options.length; i++) {
            fontHeights[i] = FontUtil.getDefaultFont().getHeight(this.options[i]);
        }
    }

    public int selected() {
        return selected;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        for (int i = 0; i < options.length; i++) {
            String option = options[i];
            int offset = i*(optionHeight+3);
            Render.drawRect(x, y+offset, x+width, y+optionHeight+offset, color);
            Render.drawRect(x+0.5, y+0.5+offset, x+width-0.5, y+optionHeight-0.5+offset, backgroundColor);
            if (selected == i) {
                Render.drawRect(x+1.5, y+1.5+offset, x+width-1.5, y+optionHeight-1.5+offset, color);
            }
            TTFFontRenderer tf = FontUtil.getDefaultFont();
            tf.drawString(option, x+width+3, y+optionHeight/2f-fontHeights[i]/2f+1+offset, textColor);
        }
    }

    @Override
    public void clicked(int button, boolean state, int mouseX, int mouseY) {
        super.clicked(button, state, mouseX, mouseY);
        if (button == 1) selected = -1;
        else {
            for (int i = 0; i < options.length; i++) {
                int offset = i*(optionHeight+3);
                if (mouseX >= x && mouseY >= y+offset && mouseX <= x+width && mouseY <= y+optionHeight+offset) selected = i;
            }
        }
    }
}
