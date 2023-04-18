package me.skizzme.lwjglui.util;

import static org.lwjgl.opengl.GL11.*;

public class GlTransformation {
    private double rotation, rotX, rotY, rotZ;
    private double transX, transY;

    public static GlTransformation rotation(double rotation, int rotX, int rotY, int rotZ) {
        return new GlTransformation(rotation, rotX, rotY, rotZ, 0, 0);
    }

    public static GlTransformation transform(float transX, float transY) {
        return new GlTransformation(0, 0, 0, 0, transX, transY);
    }

    public static GlTransformation rotationWithAxis(double rotation, float rotX, float rotY, float rotZ, float transX, float transY) {
        return new GlTransformation(rotation, rotX, rotY, rotZ, transX, transY);
    }

    private GlTransformation(double rotation, float rotX, float rotY, float rotZ, float transX, float transY) {
        this.rotation = rotation;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.transX = transX;
        this.transY = transY;
    }

    public void apply() {
        glTranslated(transX, transY, 0);
        glRotated(rotation, rotX, rotY, rotZ);
    }

    public void remove() {
        glRotated(-rotation, rotX, rotY, rotZ);
        glTranslated(-transX, -transY, 0);
    }
}