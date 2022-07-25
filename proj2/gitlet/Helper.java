package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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

    public static Commit oldCommit(String id, Repository repository) {
        File oldCommitPath = join(repository.getCommitsDir(), id);
        return readObject(oldCommitPath, Commit.class);
    }

    public static void logHelper(Commit currentCommit) {
        System.out.println("===");
        System.out.println("commit " + currentCommit.sha1());
        if (currentCommit.grandParent() != null) {
            System.out.println("Merge: " + currentCommit.parent().substring(0, 7));
            System.out.print(" " + currentCommit.grandParent().substring(0, 7));
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

    public static void createSpecific(BlobFile blobFile, Repository repository) {
        try {
            File file = join(repository.getBlobsDir(), blobFile.sha1());
            file.createNewFile();
            writeObject(file, blobFile);
        } catch (IOException exception) {
            System.out.println("IOException occurred creating BlobFile.");
        }
    }

    public static void branchHelp(String branchName, Repository repository) {
        if (!Objects.requireNonNull(plainFilenamesIn(repository.getRefsDir())).contains(branchName)){
            System.out.println("No such branch exists.");
            return;
        }
        File path = new File(readContentsAsString(repository.getHeadFile()));
        String branch = path.getName();
        if (branch.equals(branchName)){
            System.out.println("No need to checkout the current branch.");
            return;
        }
        Commit commit = currentCommit(repository);
        writeContents(repository.getHeadFile(), join("refs", branchName).getPath());
        Commit help = currentCommit(repository);
        for (String string: Objects.requireNonNull(plainFilenamesIn(repository.getCWD()))) {
            if (!commit.hashMap().containsKey(string)){
                if (help.hashMap().containsKey(string)){
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    return;
                }
            }
        }
        if (!commit.sha1().equals(help.sha1())){
            for (String string : help.hashMap().keySet()) {
                String blobHash = help.hashMap().get(string);
                create(blobHash, string, repository);
            }
            for (String string : commit.hashMap().keySet()) {
                if (!help.hashMap().containsKey(string)){
                    File gone = join(repository.getCWD(), string);
                    restrictedDelete(gone);
                }
            }
        }
    }

}
