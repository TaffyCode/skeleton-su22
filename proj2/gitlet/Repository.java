package gitlet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


import static gitlet.Utils.*;

// TODO: any imports you need here
import org.junit.Test;

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */


    private Commit commit;
    private String headPointer;
    private String head = "master";
    public static final int ID_LENGTH = 40;



    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");
    public static final File BRANCHES_DIR = join(GITLET_DIR, "branches");
    public static final File CURRENT_BRANCH = join(GITLET_DIR, "ACTIVE_BRANCH.txt");
    public static final File STAGING_AREA = join(GITLET_DIR, "staging-area");
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    public static final File HEAD = join(GITLET_DIR, "HEAD.txt");
    public StagingArea stagingArea = STAGING_AREA.exists() ? Utils.readObject(Repository.STAGING_AREA, StagingArea.class) : new StagingArea();
    public Repository() {

    }

    public void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        GITLET_DIR.mkdir();
        BRANCHES_DIR.mkdir();
        COMMITS_DIR.mkdir();
        OBJECTS_DIR.mkdir();

        try {
            HEAD.createNewFile();
        }
        catch (IOException exception) { System.out.println("IOException occurred while creating text file."); }
        try {
            CURRENT_BRANCH.createNewFile();
        }
        catch (IOException exception) { System.out.println("IOException occurred while creating current branch file."); }

        stagingArea = new StagingArea();
        Utils.writeObject(Repository.STAGING_AREA, stagingArea);

        Commit initialCommit = new Commit(new HashMap<>(), "initial commit", new ArrayList<String>(), new HashMap<String, String>());
        Utils.writeObject(initialCommit.file(), initialCommit);

        Branch initial = new Branch(initialCommit);
        File branchFile = Utils.join(Repository.BRANCHES_DIR, initial.branch);
        Utils.writeObject(branchFile, initial);
        Utils.writeContents(Repository.CURRENT_BRANCH, initial.branch);
    }

    public void add(String fileName) {
        File file = Helper.getFileCWD(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        if (stagingArea.add(file)) {
            Utils.writeObject(Repository.STAGING_AREA, stagingArea);
        }
    }

    public void commit(String commitMessage) {
        if (stagingArea.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        } else if (Objects.equals(commitMessage, " ")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        HashMap<String, String> addedMap = stagingArea.getAdded();
        Utils.writeObject(Repository.STAGING_AREA, stagingArea);
        ArrayList<String> parents = new ArrayList<>();


        String HEADCommitID = Utils.readContentsAsString(HEAD);
        File commitFile = Utils.join(Repository.COMMITS_DIR, HEADCommitID);

        parents.add(HEADCommitID);
        Commit commit = new Commit(addedMap, commitMessage, parents, stagingArea.prepForCommit());
        Utils.writeObject(commit.file(), commit);
        Utils.writeContents(HEAD, commit.ID());
        Branch currentBranch = new Branch(commit, Utils.readContentsAsString(CURRENT_BRANCH));
        File file = Utils.join(Repository.BRANCHES_DIR, currentBranch.branch);
        Utils.writeObject(file, currentBranch);
        Utils.writeContents(Repository.CURRENT_BRANCH, currentBranch.branch);
    }

    public void rm() {

    }

    public void log() {
        File commitFile = Utils.join(Repository.COMMITS_DIR, Utils.readContentsAsString(HEAD));
        Commit HEADCommit = Utils.readObject(commitFile, Commit.class);
        Commit current = HEADCommit;

        StringBuilder logBuilder = new StringBuilder();

//        while (true) {
//            logBuilder.append(current.getLog()).append("\n");
//            ArrayList<String> parents = current.getParents();
//            if (parents.size() == 0) {
//                break;
//            }
//            String nextCommitId = parents.get(0);
//            Commit nextCommit = Commit.fromFile(nextCommitId);
//            current = nextCommit;
//        }
//        System.out.print(logBuilder);
    }

    public void globalLog() {

    }

    public void find(String fileName) {

    }

    public void status() {

    }

    public void checkout(String[] file) {
        if (file [2] == null) {
            String path = Helper.getFileCWD(file[1]).getPath();
            File commitNewFileOne = Utils.join(Repository.COMMITS_DIR, Utils.readContentsAsString(HEAD));
            String temp = Utils.readObject(commitNewFileOne, Commit.class).added().get(path);
            Utils.readObject(Utils.join(Repository.OBJECTS_DIR, temp.substring(0, 2), temp.substring(2)), BlobFile.class).write();
        }
        else if (Objects.equals(file[1], "--")) {
            String tempID = "";
            for (String commitId : Objects.requireNonNull(plainFilenamesIn(COMMITS_DIR))) {
                if (commitId.contains(file[2])) {
                    tempID = commitId;
                }
            }
            String path = Helper.getFileCWD(file[2]).getPath();
            File commitNewFileTwo = Utils.join(Repository.COMMITS_DIR, tempID);
            String temp = Utils.readObject(commitNewFileTwo, Commit.class).added().get(path);
            Utils.readObject(Utils.join(Repository.OBJECTS_DIR, temp.substring(0, 2), temp.substring(2)), BlobFile.class).write();
        }

    }

    public void branch(String branchName) {

    }

    public void rmBranch(String branchName) {

    }

    public void reset(String ID) {

    }

    public void merge(String branchName) {

    }
}
