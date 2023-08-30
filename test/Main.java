import me.skizzme.lwjglui.NativeLoader;
import me.skizzme.lwjglui.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Native;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
//        loadJarDll("lwjgl.dll");
        if ((args.length > 0 && !args[0].equals("no_natives") || args.length == 0)) NativeLoader.loadNatives();
        Window w = new Window("test", 1920/2, 1080/2, true);
        new Thread(w::show).start();
        w.setScreen(new MainScreen(w));
    }

}