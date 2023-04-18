package me.skizzme.lwjglui.elements.impl;

import me.skizzme.lwjglui.Window;
import me.skizzme.lwjglui.elements.Element;
import me.skizzme.lwjglui.fonts.FontUtil;
import me.skizzme.lwjglui.fonts.TTFFontRenderer;
import me.skizzme.lwjglui.keyboard.KeyboardHelper;
import me.skizzme.lwjglui.util.Animation;
import me.skizzme.lwjglui.util.GlTransformation;
import me.skizzme.lwjglui.util.Render;

import java.util.ArrayList;
import java.util.Arrays;

public class DefaultTextbox extends Element {
    private String input = "";
    private int caretPosition = 0, color, backgroundColor;
    private Animation caretAnimation;
    private long last_type_time = System.currentTimeMillis();

    public DefaultTextbox(int color, int backgroundColor, int x, int y, int width) {
        super(x, y, width, 13);
        this.color = color;
        this.backgroundColor = backgroundColor;
        caretAnimation = new Animation(x, 0, Window.window().width);
    }

    public String input() {
        return input;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        Render.drawRect(x, y, x+width, y+height, color);
        Render.drawRect(x+0.5, y+0.5, x+width-0.5, y+height-0.5, backgroundColor);
        TTFFontRenderer tf = FontUtil.getFont("/me/skizzme/lwjglui/assets/Comfortaa-Regular.ttf", 20);
        float w = tf.getWidth(input);
        if (w > this.width) {
            Render.initMask();
            Render.drawRect(x, y, x+width, y+height, -1);
            Render.useMask();
            GlTransformation t1 = GlTransformation.transform(-(w-this.width), 0);
            t1.apply();
            tf.drawString(input, x+1, y+2, -1);
            t1.remove();
            Render.disableMask();
        } else {
            tf.drawString(input, x+1, y+2, -1);
        }
        float st = tf.getWidth(input.substring(0, caretPosition))-2;
        caretAnimation.animateBiLinearNoMaxMin(x+st, 0.001, 1);
        double cX = Math.min(caretAnimation.getValue()+1-Math.max(w-this.width, 0), this.width+x-0.5);
        if ((System.currentTimeMillis()-this.last_type_time) % 1000 < 500 && this.selected) Render.drawRect(cX, y+1.5, cX+0.5, y+tf.getHeight("a")+1.5, -1);
    }

    private int[] getCtrlDeleteWord() {
        String[] word_splits = new String[] {" ", "!", "\"", "#", "%", "&", "(", ")", "*", ",", "-", ".", "/", ":", ";", "?", "@", "[", "\\", "]", "_", "{", "}"};
        String[] words = input.split("[" + String.join("\\", word_splits) + "]");
        int startPosition = 0;
        for (String word : words) {
            int endPosition = startPosition+word.length();

            if (caretPosition >= startPosition && caretPosition <= endPosition+1) {
                return new int[] {startPosition, endPosition+1};
            }

            if (word.length() == 0) {
                startPosition = startPosition+1;
            } else {
                startPosition = endPosition+1;
            }
        }
        return new int[] {startPosition,input.length()};
    }

    @Override
    public void mouseClick(int button, boolean state, int mouseX, int mouseY) {
        super.mouseClick(button, state, mouseX, mouseY);
        this.selected = hovering(mouseX, mouseY);
    }

    @Override
    public void key(int keyCode, char charIn, boolean pressed) {
        super.key(keyCode, charIn, pressed);
        caretPosition = Math.max(caretPosition, 0);

        if (pressed) {
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
                        if (KeyboardHelper.ctrl()) {
                            int[] word = getCtrlDeleteWord();
                            input = input.substring(0, Math.max(word[0], 0)) + input.substring(Math.min(word[1], caretPosition));
                            caretPosition -= caretPosition-word[0];
                            caretPosition = Math.max(caretPosition, 0);
                        } else {
                            input = input.substring(0, caretPosition - 1) + input.substring(caretPosition);
                            caretPosition--;
                        }
                    } else if (keyCode == 211 && caretPosition != input.length()) {
                        input = input.substring(0, caretPosition) + input.substring(caretPosition+1);
                    }
                }

                if (keyCode != 14 && keyCode != 211 && Character.getType(charIn) != 15) {
                    if (KeyboardHelper.ctrl()) return;
                    input = input.substring(0, caretPosition) + charIn + input.substring(caretPosition);
                    caretPosition++;
                }
            }
        }
    }
}
