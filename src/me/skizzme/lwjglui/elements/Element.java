package me.skizzme.lwjglui.elements;

import java.util.ArrayList;

public abstract class Element {
    public int x, y, width, height;
    protected boolean selected;
    protected ArrayList<Element> children = new ArrayList<>();

    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isSelected() {
        return selected;
    }

    public void draw(int mouseX, int mouseY) {
        for (Element c : children) {
            c.draw(mouseX, mouseY);
        }
    }

    public void clicked(int button, boolean state, int mouseX, int mouseY) {
        for (Element c : children) {
            c.clicked(button, state, mouseX, mouseY);
        }
    }

    public void mouseClick(int button, boolean state, int mouseX, int mouseY) {
        for (Element c : children) {
            c.mouseClick(button, state, mouseX, mouseY);
        }
    }

    public void key(int keyCode, char charIn, boolean state) {
        for (Element c : children) {
            c.key(keyCode, charIn, state);
        }
    }

    public boolean hovering(int mouseX, int mouseY) {
        return (mouseX >= x && mouseX <= x+width) && (mouseY >= y && mouseY <= y+height);
    }
}
