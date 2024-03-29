import me.skizzme.lwjglui.GuiScreen;
import me.skizzme.lwjglui.Window;
import me.skizzme.lwjglui.elements.impl.*;
import me.skizzme.lwjglui.fonts.FontUtil;
import me.skizzme.lwjglui.fonts.TTFFontRenderer;
import me.skizzme.lwjglui.util.Render;

import java.util.ArrayList;

public class MainScreen extends GuiScreen {

    private double testPos = 0;
    private boolean testPosState = false, loading = false;
    private ArrayList<Integer> loaded = new ArrayList<>();
    private long startTime = -1;
    private float value = 0;
    private long lastSet = System.currentTimeMillis();
    private DefaultLoadbar loadbar;

    public MainScreen(Window window) {
        super(window);
    }

    @Override
    public void initGui() {
        this.elements.add(new DefaultTextbox(-2, 0xff606060, 50, 50, 100));
        this.elements.add(new DefaultCheckbox("Test", -1, 0xff606060, -1, false, 50, 80, 10, 10));
        this.elements.add(new DefaultMultipleChoice(-1, 0xff606060, -1, -1, 100, 80, 10, 10, "op1", "op2", "op3"));
        this.loadbar = new DefaultLoadbar(0, -1, 0xff606060, 50, 120, 100, 10);
        this.elements.add(loadbar);
        this.elements.add(new DefaultLoadcircle2(-1, 165, 150, 8));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);
        if (window.closeRequested) window.close = true;
        if (testPosState) {
            testPos -= 30*this.fps;
        } else {
            testPos += 30*this.fps;
        }
        if (testPos <= 0) {
            testPosState = false;
        }
        if (testPos >= 200) {
            testPosState = true;
        }
        Render.drawRect(20+testPos, 20, 40+testPos, 30, 0xff404040);
        FontUtil.getDefaultFont().drawString("test", 20, 20, 0xffffffff);
        if (this.startTime == -1) {
            this.startTime = this.lastFrame;
        }
        if (System.currentTimeMillis()-lastSet >= 250) {
            value+=5;
            this.loadbar.update(value);
            lastSet = System.currentTimeMillis();
        }
//        FontUtil.getDefaultFont().drawString("" + Math.max(2-((float) (this.lastFrame-this.startTime)/1000000000), 0), 20, 30, 0xffffffff);
//        if (this.lastFrame-this.startTime > 2000000000 && !loading) {
//            loading = true;
//            for (int i = 20; i < 35; i++) {
//                FontUtil.createFont("/me/skizzme/lwjglui/assets/Comfortaa-Regular.ttf", (int) (i*1.6));
//                loaded.add(i);
//            }
//        }

        // int i : loaded
        for (int i = 20; i < 35; i++) {
            TTFFontRenderer tf = FontUtil.getFont("/me/skizzme/lwjglui/assets/Comfortaa-Regular.ttf", (int)(i*2));
            tf.drawString("test", 20, 35+(i-20)*10+tf.getHeight("test"), 0xffffffff);
        }
        FontUtil.getFont("/me/skizzme/lwjglui/assets/Comfortaa-Regular.ttf", 32).drawString("test", 60, 35, 0xffffffff);
    }
}
