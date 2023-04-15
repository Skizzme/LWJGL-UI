package me.skizzme.lwjglui.elements;

public abstract class Element {
    public int x, y, width, height;
    protected boolean selected;

    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isSelected() {
        return selected;
    }

    public abstract void draw(int mouseX, int mouseY);

    public void click(int button, boolean state, int mouseX, int mouseY) {}

    public void key(int keyCode, char charIn, boolean state) {}

    public boolean hovering(int mouseX, int mouseY) {
        return (mouseX >= x && mouseX <= x+width) && (mouseY >= y && mouseY <= y+height);
    }
}
