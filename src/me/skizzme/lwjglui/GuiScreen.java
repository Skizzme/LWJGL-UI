package me.skizzme.lwjglui;

import me.skizzme.lwjglui.elements.Element;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

public abstract class GuiScreen {

    public int width, height, midX, midY;
    public ArrayList<Element> elements = new ArrayList<>();
    public Window window;
    public long lastFrame = 0;
    public double fps = System.nanoTime();

    public GuiScreen(Window window) {
        this.window = window;
        this.width = Display.getWidth()/2;
        this.height = Display.getHeight()/2;
        this.midX = width/2;
        this.midY = height/2;
    }

    public void initGui() {}

    public void drawScreen(int mouseX, int mouseY) {
        for (Element element : elements) {
            element.draw(mouseX, mouseY);
        }
    }

    public void handleClick(int button, boolean state, int mouseX, int mouseY) {
        for (Element element : elements) {
            if (element.hovering(mouseX, mouseY)) element.clicked(button, state, mouseX, mouseY);
            element.mouseClick(button, state, mouseX, mouseY);
        }
    }

    public void keyState(int keyCode, char charIn, boolean state) {
        for (Element element : elements) {
            if (element.isSelected()) element.key(keyCode, charIn, state);
        }
    }

    public void mouseMove(int mouseX, int mouseY) {

    }

}
