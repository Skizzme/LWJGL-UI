package me.skizzme.lwjglui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NativeLoader {

    public static void loadNatives() throws IOException {
        String tDir = System.getProperty("java.io.tmpdir");
        File nativesFolder = new File(tDir + "\\lwjgl_natives");
        if (!nativesFolder.exists()) {
            nativesFolder.mkdirs();
        }
        for (String p : nativePaths) {
            InputStream in = NativeLoader.class.getResourceAsStream(p);
            if (in == null) {
                System.out.println("Native " + p + " was not found.");
                continue;
            }
            System.out.println("Loaded native \"" + p + "\"");
            byte[] buffer = new byte[1024];
            String[] split = p.split("/");
            File temp = new File(nativesFolder + "\\" + split[split.length-1]);
            if (temp.exists()) continue;
            FileOutputStream fos = new FileOutputStream(temp);

            int read;
            while((read = in.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
            fos.close();
            in.close();
            temp.deleteOnExit();
        }

        System.setProperty("org.lwjgl.librarypath", nativesFolder.getAbsolutePath());
    }

    private static String[] nativePaths = new String[] {
            "/natives/linux/libjinput-linux.so",
            "/natives/linux/libjinput-linux64.so",
            "/natives/linux/liblwjgl.so",
            "/natives/linux/liblwjgl64.so",
            "/natives/linux/libopenal.so",
            "/natives/linux/libopenal64.so",
            "/natives/macosx/libjinput-osx.dylib",
            "/natives/macosx/liblwjgl.dylib",
            "/natives/macosx/openal.dylib",
            "/natives/solaris/liblwjgl.so",
            "/natives/solaris/liblwjgl64.so",
            "/natives/solaris/libopenal.so",
            "/natives/solaris/libopenal64.so",
            "/natives/windows/jinput-dx8.dll",
            "/natives/windows/jinput-dx8_64.dll",
            "/natives/windows/jinput-raw.dll",
            "/natives/windows/jinput-raw_64.dll",
            "/natives/windows/lwjgl.dll",
            "/natives/windows/lwjgl64.dll",
            "/natives/windows/OpenAL32.dll",
            "/natives/windows/OpenAL64.dll"
    };

}
