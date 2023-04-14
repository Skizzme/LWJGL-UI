package me.skizzme.lwjglui.mouse;

public class MouseHelper {

    private boolean[] heldButtons = new boolean[3];
    private int[] heldStartPosition = new int[2];
    public int deltaX, deltaY;

    public void handleMouse(int button, boolean pressed, int x, int y, int deltaX, int deltaY) {
        if (button != -1) {
            heldButtons[button] = pressed;
            if (pressed) {
                heldStartPosition[0] = x;
                heldStartPosition[1] = y;
            }
        }
        this.deltaY+=deltaY;
        this.deltaX+=deltaX;
    }

    public boolean isButtonPressed(int button) {
        if (button > 2) {
            return false;
        }
        return heldButtons[button];
    }

    public int getHeldX() {
        return heldStartPosition[0];
    }

    public int getHeldY() {
        return heldStartPosition[1];
    }

}
