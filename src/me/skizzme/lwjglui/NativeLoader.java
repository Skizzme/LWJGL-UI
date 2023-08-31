package me.skizzme.lwjglui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
            "/lwjgl_natives/linux/libjinput-linux.so",
            "/lwjgl_natives/linux/libjinput-linux64.so",
            "/lwjgl_natives/linux/liblwjgl.so",
            "/lwjgl_natives/linux/liblwjgl64.so",
            "/lwjgl_natives/linux/libopenal.so",
            "/lwjgl_natives/linux/libopenal64.so",
            "/lwjgl_natives/macosx/libjinput-osx.dylib",
            "/lwjgl_natives/macosx/liblwjgl.dylib",
            "/lwjgl_natives/macosx/openal.dylib",
            "/lwjgl_natives/solaris/liblwjgl.so",
            "/lwjgl_natives/solaris/liblwjgl64.so",
            "/lwjgl_natives/solaris/libopenal.so",
            "/lwjgl_natives/solaris/libopenal64.so",
            "/lwjgl_natives/windows/jinput-dx8.dll",
            "/lwjgl_natives/windows/jinput-dx8_64.dll",
            "/lwjgl_natives/windows/jinput-raw.dll",
            "/lwjgl_natives/windows/jinput-raw_64.dll",
            "/lwjgl_natives/windows/lwjgl.dll",
            "/lwjgl_natives/windows/lwjgl64.dll",
            "/lwjgl_natives/windows/OpenAL32.dll",
            "/lwjgl_natives/windows/OpenAL64.dll"
    };

}
