package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents a gitlet commit object.
 *
 * @author Raphael Pelayo
 */
public class Commit implements Serializable {

    private String commitMessage;
    private String parent;
    private String dte;
    private HashMap<String, String> hashMap = new HashMap<>();

    private String sha1;

    private String grandParent;

    private HashSet<String> mergeHelp = new HashSet<>();

    public HashSet<String> mergeHelp() { return mergeHelp; }

    private boolean check = false;

    private HashSet<String> checkName = new HashSet<>();

    public Commit(String commitMessage, String parent) {
        this.commitMessage = commitMessage;
        this.parent = parent;
        if (parent == null) {
            Date formatted = new Date(70, Calendar.JANUARY, 1, 0, 0, 0);
            this.dte = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy").format(formatted) + " +0630";
        } else {
            this.dte = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy").format(new Date()) + " +0630";
        }
    }

    public void updateSHA1(String newSHA1) {
        this.sha1 = newSHA1;
    }

    public void updateGrandparent(String grandParent) { this.grandParent = grandParent; }

    public void updateHashMap(HashMap<String, String> newHashMap) {
        this.hashMap = newHashMap;
    }

    public String sha1() {
        return sha1;
    }

    public HashMap<String, String> hashMap() {
        return this.hashMap;
    }

    public String parent() {
        return parent;
    }

    public String grandParent() {
        return grandParent;
    }

    public String date() {
        return dte;
    }

    public String commitMessage() {
        return commitMessage;
    }

    public void checkChange(boolean check) {
        this.check = check;
    }

    public HashSet<String> checkName() {
        return this.checkName;
    }

    public boolean check() { return this.check; }
}
