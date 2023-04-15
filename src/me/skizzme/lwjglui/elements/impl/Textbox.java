package me.skizzme.lwjglui.elements.impl;

import me.skizzme.lwjglui.Window;
import me.skizzme.lwjglui.elements.Element;
import me.skizzme.lwjglui.fonts.FontUtil;
import me.skizzme.lwjglui.fonts.TTFFontRenderer;
import me.skizzme.lwjglui.util.Animation;
import me.skizzme.lwjglui.util.Render;

import java.util.ArrayList;

public class Textbox extends Element {
    private String input = "";
    private int caretPosition = 0;
    private Animation caretAnimation;
    private long last_type_time = System.currentTimeMillis();

    public Textbox(int x, int y, int width, int height) {
        super(x, y, width, height);
        caretAnimation = new Animation(x, 0, Window.window().width);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        this.selected = true;
        TTFFontRenderer tf = FontUtil.getFont("/me/skizzme/lwjglui/assets/Comfortaa-Regular.ttf", 20);
        tf.drawString(input, x, y, -1);
        float st = tf.getWidth(input.substring(0, caretPosition))-2;
        caretAnimation.animateLinear(x+st, 1);
        System.out.println(caretAnimation.getValue());
        if ((System.currentTimeMillis()-this.last_type_time) % 1000 < 500) Render.drawRect(caretAnimation.getValue(), y, caretAnimation.getValue()+0.5, y+tf.getHeight("a"), -1);
        //205 = right
        //203 = left
        // 200 = top
        // 208 = bottom
    }

    @Override
    public void key(int keyCode, char charIn, boolean pressed) {
        caretPosition = Math.max(caretPosition, 0);
        if (pressed) {
            System.out.println(keyCode);
            this.last_type_time = System.currentTimeMillis();

            if (keyCode == 205) {
                caretPosition++;
                caretPosition = Math.min(caretPosition, input.length());
            } else if (keyCode == 203) {
                caretPosition--;
                caretPosition = Math.max(caretPosition, 0);
            } else {
                if (caretPosition != 0) {
                    if (keyCode == 14) {
                        input = input.substring(0, caretPosition - 1) + input.substring(caretPosition);
                        caretPosition--;
                    } else if (keyCode == 211 && caretPosition != input.length()) {
                        input = input.substring(0, caretPosition) + input.substring(caretPosition+1);
                    }
                }
                if (keyCode != 14 && keyCode != 211) {
                    input = input.substring(0, caretPosition) + charIn + input.substring(caretPosition);
                    caretPosition++;
                }
            }
        }
    }
}
