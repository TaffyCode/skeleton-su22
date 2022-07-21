package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.Locale;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    private HashMap<String, String> blobs;
    private ArrayList parent;
    private HashMap<String, String> added;
    private String commitMessage;
    private Date date;
    private String hash;
    private String ID;
    private File file;
    public Commit(HashMap<String, String> blobs, String commitMessage, ArrayList<String> parent, HashMap<String, String> added) {
        this.blobs = blobs;
        this.parent = parent;
        this.commitMessage = commitMessage;
        this.added = added;
        this.date = new Date(0);
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        this.ID = Utils.sha1(this.commitMessage, dateFormat.format(date), this.parent.toString(), this.blobs.toString());
        this.file = Utils.join(Repository.COMMITS_DIR, this.ID);
    }

    public static Commit fromSHA1(String ID) {
        File file = Utils.join(Repository.COMMITS_DIR, ID);
        return Utils.readObject(file, Commit.class);
    }

    public boolean restoreTrackedFile(String filePath) {
        String blobID = added.get(filePath);

        if (blobID == null) {
            return false;
        }

        Helper.blobFromFile(blobID).write();
        return true;
    }

    public HashMap<String, String> blobs() { return blobs; }
    public String commitMessage() { return commitMessage; }
    public Date date() { return date; }
    public String hash() { return hash; }

    public String ID() { return ID; }

    public HashMap<String, String> added() { return added; }

    public File file() { return file; }

}
