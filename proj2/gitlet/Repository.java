package gitlet;

import java.io.*;
import java.util.*;
import static gitlet.Utils.*;

/**
 * Represents a gitlet repository.
 * does at a high level.
 *
 * @author Raphael Pelayo
 */
public class Repository {
    /**
     *
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */


    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");
    public static final File BLOBS_DIR = join(GITLET_DIR, "blobs");
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    public static final File HEAD_FILE = join(GITLET_DIR, "HEAD");
    public static final File STAGING_FILE = join(GITLET_DIR, "staging-area");
    public static final File REMOTES_DIR = join(GITLET_DIR, "remotes");


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
        try {
            COMMITS_DIR.mkdirs();
            BLOBS_DIR.mkdirs();
            REFS_DIR.mkdirs();
            HEAD_FILE.createNewFile();
            STAGING_FILE.createNewFile();
            REMOTES_DIR.mkdirs();
        } catch (IOException exception) {
            System.out.println("IOException occurred on init.");
        }

        Commit commit = new Commit("initial commit", null);
        Helper.writeCommit(commit, "main", this);
        writeContents(HEAD_FILE, join("refs", "main").getPath());
        StagingArea stagingArea = new StagingArea(this);
        writeObject(STAGING_FILE, stagingArea);
    }

    public void add(String add) {
        File addFile = join(CWD, add);
        if (!addFile.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        StagingArea stagingArea = readObject(STAGING_FILE, StagingArea.class);
        BlobFile blobs = new BlobFile(readContents(addFile));
        String stringBlob = blobs.sha1();
        Commit currentCommit = Helper.currentCommit(this);
        if (currentCommit.hashMap().containsKey(add)) {
            if (!currentCommit.hashMap().get(add).equals(stringBlob)) {
                try {
                    File file = join(BLOBS_DIR, blobs.sha1());
                    file.createNewFile();
                    writeObject(file, blobs);
                } catch (IOException exception) {
                    System.out.println("IOException occurred in add.");
                    return;
                }
                stagingArea.add(add, stringBlob);
            }
            if (stagingArea.removed().contains(add)) {
                stagingArea.removed().remove(add);
            }
        } else {
            try {
                File file = join(BLOBS_DIR, blobs.sha1());
                file.createNewFile();
                writeObject(file, blobs);
            } catch (IOException exception) {
                System.out.println("IOException occurred in add.");
                return;
            }
            stagingArea.add(add, stringBlob);
        }
        writeObject(STAGING_FILE, stagingArea);
    }

    public void rm(String rm) {
        StagingArea stagingArea = readObject(STAGING_FILE, StagingArea.class);
        File file = join(CWD, rm);
        Commit previousCommit = Helper.currentCommit(this);

        if (stagingArea.added().containsKey(rm)) {
            stagingArea.added().remove(rm);
        } else if (previousCommit.hashMap().containsKey(rm)) {
            stagingArea.removed().add(rm);
            Utils.restrictedDelete(rm);
        } else {
            System.out.println("No reason to remove the file.");
            return;
        }
        writeObject(STAGING_FILE, stagingArea);
    }

    public void rmBranch(String rm) {
        if (!Objects.requireNonNull(plainFilenamesIn(REFS_DIR)).contains(rm)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        File path = new File(readContentsAsString(HEAD_FILE));
        String branch = path.getName();
        if (branch.equals(rm)) {
            System.out.println("Cannot remove the current branch.");
        }
        File remove = join(REFS_DIR, rm);
        remove.delete();
    }

    public void commit(String commitMessage) {

        if (Objects.equals(commitMessage, "")) {
            System.out.println("Please enter a commit message.");
            return;
        }

        StagingArea stagingArea = readObject(STAGING_FILE, StagingArea.class);

        if (stagingArea.added().isEmpty() && stagingArea.removed().isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }

        Commit parentCommit = Helper.currentCommit(this);
        String parentSHA1 = parentCommit.sha1();
        Commit currentCommit = new Commit(commitMessage, parentSHA1);
        currentCommit.updateHashMap(parentCommit.hashMap());
        for (String string : stagingArea.added().keySet()) {
            currentCommit.hashMap().put(string, stagingArea.added().get(string));
        }
        for (String string : stagingArea.removed()) {
            currentCommit.hashMap().remove(string);
        }
        File path = new File(readContentsAsString(HEAD_FILE));
        Helper.writeCommit(currentCommit, path.getName(), this);
        stagingArea.empty();
        writeObject(STAGING_FILE, stagingArea);
    }

    public void checkout(String file) {
        Commit previousCommit = Helper.currentCommit(this);
        if (previousCommit.hashMap().containsKey(file)) {
            File blob = join(BLOBS_DIR, previousCommit.hashMap().get(file));
            BlobFile blobFile = readObject(blob, BlobFile.class);
            File newVersion = join(CWD, file);
            try {
                newVersion.createNewFile();
            } catch (IOException exception) {
                System.out.println("IOException occurred during checkout.");
                return;
            }
            writeContents(newVersion, (Object) blobFile.blobContent());
        } else {
            System.out.println("File does not exist in that commit.");
        }
    }

    public void checkout(String id, String file) {
        if (!plainFilenamesIn(COMMITS_DIR).contains(id)) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit previousCommit = Helper.oldCommit(id, this);

        if (previousCommit.hashMap().containsKey(file)) {
            String blobs = previousCommit.hashMap().get(file);
            Helper.create(blobs, file, this);
        } else {
            System.out.println("File does not exist in that commit.");
        }
    }

    public void checkoutForBranches(String branchName) {
        if (!Objects.requireNonNull(plainFilenamesIn(REFS_DIR)).contains(branchName)) {
            System.out.println("No such branch exists.");
            return;
        }
        File path = new File(readContentsAsString(HEAD_FILE));
        String branch = path.getName();
        if (branch.equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }
        Commit previousCommit = Helper.currentCommit(this);
        writeContents(HEAD_FILE, join("refs", branchName).getPath());
        Commit previousCommitTwo = Helper.currentCommit(this);
        for (String string : Objects.requireNonNull(plainFilenamesIn(CWD))) {
            if (!previousCommit.hashMap().containsKey(string)) {
                if (previousCommitTwo.hashMap().containsKey(string)) {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                }
            }
        }
        if (!previousCommit.sha1().equals(previousCommitTwo.sha1())) {
            for (String string : previousCommitTwo.hashMap().keySet()) {
                String blob = previousCommitTwo.hashMap().get(string);
                Helper.create(blob, string, this);
            }
            for (String string : previousCommit.hashMap().keySet()) {
                if (!previousCommitTwo.hashMap().containsKey(string)) {
                    File remove = join(CWD, string);
                    Utils.restrictedDelete(remove);
                }
            }
        }
    }

    public void log() {
        Commit previousCommit = Helper.currentCommit(this);
        while (previousCommit.parent() != null) {
            Helper.logHelper(previousCommit);
            File path = join(COMMITS_DIR, previousCommit.parent());
            previousCommit = readObject(path, Commit.class);
        }
        Helper.logHelper(previousCommit);
    }

    public void status() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        System.out.println("=== Branches ===");
        File path = new File(readContentsAsString(HEAD_FILE));
        String branch = path.getName();
        for (String string : Objects.requireNonNull(plainFilenamesIn(REFS_DIR))) {
            if (string.equals(branch)) {
                System.out.println("*" + string);
                continue;
            }
            System.out.println(string);
        }

        System.out.println();

        System.out.println("=== Staged Files ===");
        StagingArea stagingArea = readObject(STAGING_FILE, StagingArea.class);
        HashMap<Integer, String> placeHolder = new HashMap<>();
        int count = 0;
        for (String string : stagingArea.added().keySet()) {
            placeHolder.put(count, string);
            count += 1;
        }
        count -= 1;
        for (Integer each : placeHolder.keySet()) {
            System.out.println(placeHolder.get(count));
            count -= 1;
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for (String string : stagingArea.removed()) {
            System.out.println(string);
        }
        System.out.println();
        Commit previousCommit = Helper.currentCommit(this);
        System.out.println("=== Modifications Not Staged For Commit ===");
        List<String> workingDirectory = plainFilenamesIn(CWD);
        HashMap<String, String> hashMap = previousCommit.hashMap();
        HashMap<String, String> added = stagingArea.added();
        assert workingDirectory != null;
        for (String string : workingDirectory) {
            File file = join(CWD, string);
            BlobFile blobFile = new BlobFile(Utils.readContents(file));
            if (hashMap.containsKey(string)) {
                if (!hashMap.get(string).equals(blobFile.sha1()) && !added.containsKey(string)) {
                    System.out.println(string + " (modified");
                }
            }
            if (added.containsKey(string)) {
                if (!added.get(string).equals(blobFile.sha1())) {
                    System.out.println(string + " (modified)");
                }
            }
        }

        System.out.println();

        System.out.println("=== Untracked Files ===");
        for (String string : Objects.requireNonNull(plainFilenamesIn(CWD))) {
            if (!previousCommit.hashMap().containsKey(string)) {
                if (!stagingArea.added().containsKey(string)) {
                    System.out.println(string);
                }
            }
        }
        System.out.println();
    }

    public void globalLog() {
        List<String> log = plainFilenamesIn(COMMITS_DIR);
        assert log != null;
        for (String string : log) {
            Commit commit = Helper.oldCommit(string, this);
            Helper.logHelper(commit);
        }
    }

    public void find(String find) {
        boolean exists = false;
        for (String sha1 : Objects.requireNonNull(plainFilenamesIn(COMMITS_DIR))) {
            Commit searchCommit = Helper.oldCommit(sha1, this);
            if (searchCommit.commitMessage().equals(find)) {
                System.out.println(sha1);
                exists = true;
            }
        }
        if (!exists) {
            System.out.println("Found no commit with that message.");
        }
    }

    public void branch(String branch) {
        if (Objects.requireNonNull(plainFilenamesIn(REFS_DIR)).contains(branch)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        File branchFile = join(REFS_DIR, branch);
        try {
            branchFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("IOException occurred during branch.");
            return;
        }
        Commit previousCommit = Helper.currentCommit(this);
        previousCommit.checkChange(true);
        File path = new File(readContentsAsString(HEAD_FILE));
        previousCommit.checkName().add(path.getName());
        previousCommit.checkName().add(branch);
        File file = join(COMMITS_DIR, previousCommit.sha1());
        writeObject(file, previousCommit);
        writeContents(branchFile, previousCommit.sha1());
    }

    public void reset(String reset) {
        if (!Objects.requireNonNull(plainFilenamesIn(COMMITS_DIR)).contains(reset)) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit previousCommit = Helper.currentCommit(this);
        Commit checkCommit = Helper.oldCommit(reset, this);
        for (String string : Objects.requireNonNull(plainFilenamesIn(CWD))) {
            if (!previousCommit.hashMap().containsKey(string)) {
                if (checkCommit.hashMap().containsKey(string)) {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    return;
                }
            } else {
                this.rm(string);
            }
        }
        try {
            if (!previousCommit.sha1().equals(checkCommit.sha1())) {
                for (String string : checkCommit.hashMap().keySet()) {
                    String blob = checkCommit.hashMap().get(string);
                    Helper.create(blob, string, this);
                }
                for (String string : previousCommit.hashMap().keySet()) {
                    if (!checkCommit.hashMap().containsKey(string)) {
                        File remove = join(CWD, string);
                        Utils.restrictedDelete(remove);
                    }
                }
            }
            File path = new File(readContentsAsString(HEAD_FILE));
            File file = join(REFS_DIR, path.getName());
            file.createNewFile();
            writeContents(file, checkCommit.sha1());
        } catch (IOException exception) {
            System.out.println("IOException occurred during reset.");
        }
    }

    public File getCWD() {
        return CWD;
    }

    public File getGitletDir() {
        return GITLET_DIR;
    }

    public File getCommitsDir() {
        return COMMITS_DIR;
    }

    public File getHeadFile() {
        return HEAD_FILE;
    }

    public File getStagingFile() {
        return STAGING_FILE;
    }

    public File getBlobsDir() {
        return BLOBS_DIR;
    }

    public File getRefsDir() {
        return REFS_DIR;
    }

    public File getRemotesDir() {
        return REMOTES_DIR;
    }
}
