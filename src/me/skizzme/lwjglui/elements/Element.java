package me.skizzme.lwjglui.elements;

public abstract class Element {
    public int x, y, width, height;

    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(int mouseX, int mouseY);

    public abstract void click(int button, boolean state);

    public boolean hovering(int mouseX, int mouseY) {
        return (mouseX >= x && mouseX <= x+width) && (mouseY >= y && mouseY <= y+height);
    }
}
