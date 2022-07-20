package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class StagingArea implements Serializable {


    private HashMap<String, String> added;
    private ArrayList<String> removed;

    private HashMap<String, String> changes;
    public StagingArea() {
        added = new HashMap<>();
        removed = new ArrayList<>();
        changes = new HashMap<>();
    }

    public HashMap<String, String> added() { return added; }

    public boolean add(File file) {
        BlobFile blob = new BlobFile(file);
        String filePath = file.getPath();
        String prevID = added.put(filePath, blob.ID());
        blob.saveFile();
        return true;
    }

    public HashMap<String, String> prepForCommit() {
        changes.putAll(added);
        for (String filePath : removed) {
            changes.remove(filePath);
        }
        added = new HashMap<>();
        return changes;
    }

    public HashMap<String, String> getAdded() { return added; }

    public boolean contains(String name) { return added.containsKey(name); }

    public void clearStage() { added = new HashMap<>(); }

    public boolean isEmpty() { return added.size() == 0; }

}