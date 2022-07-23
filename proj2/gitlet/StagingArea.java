package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StagingArea implements Serializable {

    final File stages = Repository.STAGING_FILE;
    private HashMap<String, String> added = new HashMap<>();
    private List<String> removed = new ArrayList<>();

    public void add(String file, String blobFiles) { added.put(file, blobFiles); }

    public void remove(String file) { removed.add(file); }

    public HashMap<String, String> added() { return added; }

    public List<String> removed() { return removed; }

    public void empty() {
        added.clear();
        removed.clear();
    }

}
