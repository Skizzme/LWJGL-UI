import me.skizzme.lwjglui.GuiScreen;
import me.skizzme.lwjglui.Window;
import me.skizzme.lwjglui.elements.impl.Textbox;
import me.skizzme.lwjglui.fonts.FontUtil;
import me.skizzme.lwjglui.fonts.TTFFontRenderer;
import me.skizzme.lwjglui.util.Render;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

public class MainScreen extends GuiScreen {

    private double testPos = 0;
    private boolean testPosState = false, loading = false;
    private ArrayList<Integer> loaded = new ArrayList<>();
    private long startTime = -1;

    public MainScreen(Window window) {
        super(window);
        this.elements.add(new Textbox(50, 50, 100, 20));
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
            TTFFontRenderer tf = FontUtil.getFont("/me/skizzme/lwjglui/assets/Comfortaa-Regular.ttf", (int)(i*1));
            tf.drawString("test", 20, 35+(i-20)*10+tf.getHeight("test"), 0xffffffff);
        }
    }
}
