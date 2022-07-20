package gitlet;

import java.io.File;
import java.io.Serializable;

public class BlobFile implements Serializable {
    private final File parent;

    private final byte[] content;

    private final String ID;

    private final File fileName;

    public BlobFile(File parent) {
        this.parent = parent;
        this.content = Utils.readContents(parent);
        String filePath = parent.getPath();
        this.ID = Utils.sha1(filePath, this.content);
        this.fileName = Utils.join(Repository.OBJECTS_DIR, this.ID.substring(0, 2), this.ID.substring(2));
    }

    public void saveFile() {
        File parentFile = this.parent.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Utils.writeObject(this.parent, this);
    }

    public void write() { Utils.writeContents(parent, content);}

    public String ID() { return ID; }
}
