package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Branch implements Serializable {
    public String branch;
    private String pointerID;
    private Commit HEAD;

    public Branch(Commit initialCommit) {
        branch = "master";
        this.pointerID = initialCommit.ID();
        this.HEAD = initialCommit;
    }

    public Branch(Commit currentCommit, String branchName) {
        branch = branchName;
        this.pointerID = currentCommit.ID();
        this.HEAD = currentCommit;
    }

    public String branch() {
        return this.branch;
    }

    public static void initHelper(File fileName, String ID) {
        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Utils.writeContents(fileName, ID);
    }
}
