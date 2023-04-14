import me.skizzme.lwjglui.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
//        loadJarDll("lwjgl.dll");
        if ((args.length > 0 && !args[0].equals("no_natives") || args.length == 0)) loadNatives();
        Window w = new Window("test", 1920/2, 1080/2, true);
        w.show(new MainScreen(w));
    }

    public static void loadNatives() throws IOException {
        Path nativesFolder = Files.createTempDirectory("lwjgl_natives");
        for (String p : nativePaths) {
            InputStream in = Main.class.getResourceAsStream(p);
            byte[] buffer = new byte[1024];
            String[] split = p.split("/");
            File temp = new File(nativesFolder + "\\" + split[split.length-1]);
            FileOutputStream fos = new FileOutputStream(temp);

            int read;
            while((read = in.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
            fos.close();
            in.close();
        }

//        System.load(String.valueOf(nativesFolder));
        System.setProperty("org.lwjgl.librarypath", nativesFolder.toAbsolutePath().toString());
    }

    private static String[] nativePaths = new String[] {
            "natives/linux/libjinput-linux.so",
            "natives/linux/libjinput-linux64.so",
            "natives/linux/liblwjgl.so",
            "natives/linux/liblwjgl64.so",
            "natives/linux/libopenal.so",
            "natives/linux/libopenal64.so",
            "natives/macosx/libjinput-osx.dylib",
            "natives/macosx/liblwjgl.dylib",
            "natives/macosx/openal.dylib",
            "natives/solaris/liblwjgl.so",
            "natives/solaris/liblwjgl64.so",
            "natives/solaris/libopenal.so",
            "natives/solaris/libopenal64.so",
            "natives/windows/jinput-dx8.dll",
            "natives/windows/jinput-dx8_64.dll",
            "natives/windows/jinput-raw.dll",
            "natives/windows/jinput-raw_64.dll",
            "natives/windows/lwjgl.dll",
            "natives/windows/lwjgl64.dll",
            "natives/windows/OpenAL32.dll",
            "natives/windows/OpenAL64.dll"
    };

}