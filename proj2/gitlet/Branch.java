package gitlet;

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
}
