package me.skizzme.lwjglui;

import me.skizzme.lwjglui.fonts.FontUtil;
import me.skizzme.lwjglui.keyboard.KeyboardHelper;
import me.skizzme.lwjglui.mouse.MouseHelper;
import me.skizzme.lwjglui.util.Icons;
import me.skizzme.lwjglui.util.Render;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Window {

    public long lastUpdate = System.currentTimeMillis();
    public int frames = 0, width, height, borderlessClickableHeight = 20;
    private static GuiScreen currentScreen;
    private static Window window;
    public final String title;
    public final MouseHelper mouseHelper = new MouseHelper();
    public FontUtil fontUtil;
    private boolean borderless;
    public boolean close = false, closeRequested = false;
    private long lastRepeat = System.currentTimeMillis();
    public int fps = 144;
    private boolean running = false, loading = false, loaded = false;
    private float backgroundRed = 0.0784f, backgroundGreen = 0.082f, backgroundBlue = 0.09f;

    public Window(String title, int width, int height, boolean borderless) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.borderless = borderless;
        if (borderless) {
            this.makeBorderless();
        }
        Window.window = this;
    }

    public void setBackground(float red, float green, float blue) {
        this.backgroundRed = red;
        this.backgroundGreen = green;
        this.backgroundBlue = blue;
    }

    public static Window window() {
        return window;
    }

    public void createDisplay() {
        System.out.println("creating");
        Display.setTitle(this.title);
        Display.setInitialBackground(backgroundRed,backgroundGreen,backgroundBlue);
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setResizable(false);
            Display.create(new PixelFormat(8,1,0,8));
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        System.out.println("created");
    }

    public void setIcon(String icon_32, String icon_64) {
        if (!icon_32.equals("") && !icon_64.equals("")) {
            InputStream icon32 = getClass().getResourceAsStream(icon_32);
            InputStream icon64 = getClass().getResourceAsStream(icon_64);
            try {
                Display.setIcon(new ByteBuffer[] {readImageToBuffer(icon32), readImageToBuffer(icon64)});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setScreen(GuiScreen currentScreen) {
        while (!running || !loaded) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentScreen.initGui();
        Window.currentScreen = currentScreen;
        Window.currentScreen.lastFrame = System.nanoTime();
    }

    public static GuiScreen getScreen() {
        return currentScreen;
    }

    public void run() {
        System.out.println("current");
        currentScreen = new DefaultScreen(this);
        System.out.println("currect");
        Render.textures.clear();
        running = true;
        while (!close) {
            if (Display.isCloseRequested()) {
                closeRequested = true;
            }
            if (!loading) {
                loading = true;
                FontUtil.loadDefaultFont();
                Icons.setup();
                loaded = true;
            }
            render();
            Display.sync(Display.isActive() ? fps : 30);
        }
        Display.destroy();
    }

    public void close() {
        this.close = true;
    }

    public void show() {
        close = false;
        createDisplay();
        run();
    }

    public void render() {
        render(false);
    }

    public void render(boolean checkFPS) {
        if (currentScreen == null) {
            return;
        }
        currentScreen.width = this.width;
        currentScreen.height = this.height;

        if (checkFPS && ((double) (System.nanoTime()-currentScreen.lastFrame))/1000000000d < 1d/fps) {
            return;
        }

        this.preRender();

        int mouseX = Mouse.getX()/2;
        int mouseY = (height - Mouse.getY())/2;

        if (!Mouse.isCreated() || !Keyboard.isCreated() || closeRequested) {
            return;
        }

        while (Mouse.next()) {
            int deltaX = Mouse.getDX();
            int deltaY = Mouse.getDY();
            mouseX = Mouse.getX()/2;
            mouseY = (height - Mouse.getY())/2;
            int eventButton = Mouse.getEventButton();
            if (eventButton > 2) {
                eventButton = -1;
            }
            boolean state = Mouse.getEventButtonState();
            mouseHelper.handleMouse(eventButton, state, mouseX, mouseY, deltaX, -deltaY);

            if (mouseHelper.isButtonPressed(0)) this.handleScreenMove();
            if (state) currentScreen.handleClick(eventButton, state, mouseX, mouseY);

            if (mouseHelper.isButtonPressed(0) || mouseHelper.isButtonPressed(1)) {
                currentScreen.mouseMove(mouseX, mouseY);
            }
        }

        while (Keyboard.next()) {
            int code = Keyboard.getEventKey();
            char key = Keyboard.getEventCharacter();
            boolean state = Keyboard.getEventKeyState();
            KeyboardHelper.key(code, state);
            currentScreen.keyState(code, key, state);
        }

        if (Keyboard.isRepeatEvent() && System.currentTimeMillis() - lastRepeat > 75) {
            int code = Keyboard.getEventKey();
            char key = Keyboard.getEventCharacter();
            boolean state = Keyboard.getEventKeyState();
            currentScreen.keyState(code, key, state);
            lastRepeat = System.currentTimeMillis();
        }

        currentScreen.fps = (System.nanoTime()-currentScreen.lastFrame)/100000000d;
        currentScreen.lastFrame = System.nanoTime();
        currentScreen.drawScreen(mouseX, mouseY);

        this.postRender();
    }

    private void preRender() {

        if (Display.wasResized()) {
            width = Display.getWidth();
            height = Display.getHeight();
            currentScreen.width = width;
            currentScreen.height = height;
            currentScreen.midX = width/4;
            currentScreen.midY = height/4;
            glViewport(0,0, width, height);
        }

        glClear(GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width/2d, height/2d,0,1000,3000);
        glTranslated(0, 0, -2000);
//        glScaled(scale, scale, 1);

        glClear(GL_COLOR_BUFFER_BIT);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private void postRender() {
        glDisable(GL_TEXTURE_2D);
//        glScaled(-scale, -scale, 1);

        if (glGetError() != 0) {
            System.err.println("OpenGL: " + glGetError());
        }

        Display.update();
        frames++;

        if (System.currentTimeMillis() - lastUpdate > 1000){
            lastUpdate = System.currentTimeMillis();
            frames = 0;
        }
    }

    private void handleScreenMove() {
        if (borderless && mouseHelper.getHeldY() < borderlessClickableHeight && mouseHelper.getHeldX() < Display.getWidth()/2d-25) {
            Display.setLocation(Display.getX()+mouseHelper.deltaX-mouseHelper.getHeldX()*2, Display.getY()+mouseHelper.deltaY+Display.getHeight()-mouseHelper.getHeldY()*2);
        }
    }

    private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException
    {
        BufferedImage var2 = ImageIO.read(imageStream);
        int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[])null, 0, var2.getWidth());
        ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
        int[] var5 = var3;
        int var6 = var3.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            int var8 = var5[var7];
            var4.putInt(var8 << 8 | var8 >> 24 & 255);
        }

        var4.flip();
        return var4;
    }

    public void makeBorderless() {
        this.borderless = true;
        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
    }

}
