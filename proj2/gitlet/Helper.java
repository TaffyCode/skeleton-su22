package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

public class Helper {

    public static void writeCommit(Commit commit, String branch, Repository repository) {
        try {
            final String SHA1 = Utils.sha1((Object) Utils.serialize(commit));
            commit.updateSHA1(SHA1);
            final File current = join(repository.getCommitsDir(), SHA1);
            current.createNewFile();
            writeObject(current, commit);

            final File branchName = join(repository.getRefsDir(), branch);
            branchName.createNewFile();
            writeContents(branchName, SHA1);
        } catch (IOException exception) {
            System.out.println("IOException occured while writing commit.");
        }
    }

    public static Commit currentCommit(Repository repository) {
        String path = readContentsAsString(repository.getHeadFile());
        File file = join(repository.getGitletDir(), path);
        String string = readContentsAsString(file);
        File commitPath = join(repository.getCommitsDir(), string);
        return readObject(commitPath, Commit.class);
    }

    public static Commit oldCommit(String ID, Repository repository) {
        File oldCommitPath = join(repository.getCommitsDir(), ID);
        return readObject(oldCommitPath, Commit.class);
    }

    public static void logHelper(Commit currentCommit) {
        System.out.println("===");
        System.out.println("commit " + currentCommit.SHA1());
        if (currentCommit.grandParent() != null) {
            System.out.println("Merge: " + currentCommit.parent().substring(0, 7) + " " + currentCommit.grandParent().substring(0, 7));
        }
        System.out.println("Date: " + currentCommit.date());
        System.out.println(currentCommit.commitMessage());
        System.out.println();
    }

    public static void create(String blobs, String fileName, Repository repository) {
        File blobFile = join(repository.getBlobsDir(), blobs);
        BlobFile read = readObject(blobFile, BlobFile.class);
        File create = join(repository.getCWD(), fileName);
        try {
            create.createNewFile();
        } catch (IOException exception) {
            System.out.println("IOException occurred when creating file.");
        }
        Utils.writeContents(create, (Object) read.blobContent());
    }

}
