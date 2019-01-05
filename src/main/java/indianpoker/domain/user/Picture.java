package indianpoker.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Picture extends AbstractEntity {
    public static final Picture EMPTY_PICTURE = new EmptyPicture();

    @Column(unique = true, nullable = false)
    private String fileName;

    @Column(unique = true, nullable = false)
    private String fileDownloadUrl;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_picture_user"))
    private User user;

    public Picture() {
    }

    public Picture(String fileName, String fileDownloadUrl) {
        this.fileName = fileName;
        this.fileDownloadUrl = fileDownloadUrl;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return false;
    }

    private static class EmptyPicture extends Picture {
        private static final String EMPTY_PICTURE = "example.jpeg";
        private static final String EMPTY_PICTURE_URL ="upload/" + EMPTY_PICTURE;

        EmptyPicture() {
            super(EMPTY_PICTURE, EMPTY_PICTURE_URL);
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    public String getFileName() {
        return fileName;
    }


    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

}
