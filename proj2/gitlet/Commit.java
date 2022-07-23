package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {

    private String commitMessage;
    private String parent;
    private String date;
    private HashMap<String, String> hashMap = new HashMap<>();

    private String SHA1;

    private String grandParent;

    private boolean check = false;

    private HashSet<String> checkName = new HashSet<>();

    public Commit(String commitMessage, String parent) {
        this.commitMessage = commitMessage;
        this.parent = parent;
        if (parent == null) {
            this.date = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy").format( new Date(70, Calendar.JANUARY, 1, 0, 0, 0)) + " +0630";
        } else {
            this.date = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy").format(new Date()) + " +0630";
        }
    }

    public void updateSHA1(String newSHA1) { this.SHA1 = newSHA1;}

    public void updateHashMap(HashMap<String, String> newHashMap) { this.hashMap = newHashMap; }

    public String SHA1() { return SHA1; }

    public HashMap<String, String> hashMap() { return this.hashMap; }

    public String parent() { return parent; }

    public String grandParent() { return grandParent; }

    public String date() { return date; }

    public String commitMessage() { return commitMessage; }

    public void checkChange(boolean check) { this.check = check; }

    public HashSet<String> checkName() { return this.checkName; }
}
