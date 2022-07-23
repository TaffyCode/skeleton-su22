package gitlet;

import java.io.File;
import java.io.Serializable;

public class BlobFile implements Serializable {
    File blob = Repository.BLOBS_DIR;
    private byte[] blobContent;
    private String SHA1;

    public BlobFile(byte[] bytes) {
        this.blobContent = bytes;
        this.SHA1 = Utils.sha1((Object) Utils.serialize((Serializable) this));
    }

    public String SHA1() {
        return SHA1;
    }

    public byte[] blobContent() {
        return blobContent;
    }

}
