package me.skizzme.lwjglui;

import me.skizzme.lwjglui.elements.impl.DefaultLoadcircle;
import me.skizzme.lwjglui.elements.impl.DefaultLoadcircle2;

public class DefaultScreen extends GuiScreen {
    private DefaultLoadcircle circle = new DefaultLoadcircle(window.width/4, window.height/4, 12);

    public DefaultScreen(Window window) {
        super(window);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        System.out.println("defualt en");
        super.drawScreen(mouseX, mouseY);
        circle.draw(mouseX, mouseY);
    }
}
