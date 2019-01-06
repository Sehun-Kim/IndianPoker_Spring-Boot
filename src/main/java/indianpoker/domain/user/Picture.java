package indianpoker.domain.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Picture {
    private static final String DEFAULT_PICTURE_NAME = "example.jpeg";

    @Column(unique = true)
    private String fileName;

    @Column(unique = true)
    private String fileDownloadUrl;

    public Picture() {
    }

    public Picture(String fileName, String fileDownloadUrl) {
        this.fileName = fileName;
        this.fileDownloadUrl = fileDownloadUrl;
    }

    public static Picture getDefaultPicture(String uploadDirUrl) {
        return new Picture(DEFAULT_PICTURE_NAME, uploadDirUrl + "/" + DEFAULT_PICTURE_NAME);
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

}
