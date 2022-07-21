package gitlet;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Blob;
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

    public static Commit getHEADCommit(Repository repository) {
        String HEADCommitID = getHeadCommitID(repository);
        return commitFromFile(HEADCommitID);
    }

    public static String getHeadCommitID(Repository repository) {
        return Utils.readContentsAsString(repository.getHEAD());
    }

    public static Commit commitFromFile(String ID) {
        File commitFile = Utils.join(Repository.COMMITS_DIR, ID);
        return Utils.readObject(commitFile, Commit.class);
    }

    public static BlobFile blobFromFile(String ID) {
        return Utils.readObject(getObjectFile(ID), BlobFile.class);
    }

    public static HashMap<String, String> commitStagingArea(StagingArea stagingArea) {
        stagingArea.changes().putAll(stagingArea.added());
        for (String filePath : stagingArea.removed()) {
            stagingArea.changes().remove(filePath);
        }
        stagingArea.wipe();
        return stagingArea.changes();
    }

}
