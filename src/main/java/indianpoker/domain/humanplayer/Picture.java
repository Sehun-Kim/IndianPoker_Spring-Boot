package indianpoker.domain.humanplayer;

import indianpoker.exception.NotImageDataException;
import org.apache.commons.io.FilenameUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class Picture {
    private static String FILE_EXTENSION_FORMAT = "((jpg|png|jpeg)$)";

    public static final Picture DEFAULT_PICTURE = new DefaultPicture();

    @Column
    private String fileId;

    @Column
    private String extension;

    @Column
    private String originalFileName;

    public Picture() {
    }

    public Picture(String originalFileName) {
        this.fileId = UUID.randomUUID().toString().replace("-", "");
        this.extension = checkExtension(originalFileName);
        this.originalFileName = originalFileName;
    }

    String checkExtension(String fileName) {
        String extension = FilenameUtils.getExtension(fileName).toLowerCase();

        if (!extension.matches(FILE_EXTENSION_FORMAT))
            throw new NotImageDataException("NOT IMAGE FILE");
        return extension;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFileIdWithExtension() {
        return fileId + "." + FilenameUtils.getExtension(originalFileName);
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    private static class DefaultPicture extends Picture {
        private static final String DEFAULT_PICTURE_NAME = "example.jpeg";

        @Override
        public String getFileIdWithExtension() {
            return DEFAULT_PICTURE_NAME;
        }

        @Override
        public String getOriginalFileName() {
            return DEFAULT_PICTURE_NAME;
        }
    }
}
