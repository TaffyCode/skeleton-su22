package gitlet;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Helper {

    public static void storeFile(String path, Object object) {
        File output = new File(path);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(output));
            out.writeObject(object);
            out.close();
        }
        catch (FileNotFoundException exception) { System.out.println("FileNotFound occurred while storing file."); }
        catch (IOException exception) { System.out.println("IOException occurred while storing file."); }
    }

    public static void writeFile(String path, String text) {
        try {
            File logFile = new File(path);
            BufferedWriter out = new BufferedWriter(new FileWriter(logFile, false));
            out.write(text);
            out.close();
        }
        catch (FileNotFoundException exception) { System.out.println("FileNotFound occurred while writing file."); }
        catch (IOException exception) { System.out.println("IOException occurred while writing file."); }
    }

    public static File getFileCWD(String name) {
        if (Paths.get(name).isAbsolute()) {
            return new File(name);
        } else {
            return Utils.join(Repository.CWD, name);
        }
    }

    public static File getObjectFile(String ID) {
        return Utils.join(Repository.OBJECTS_DIR, ID.substring(0, 2), ID.substring(2));
    }
}
