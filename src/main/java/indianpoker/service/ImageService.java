package indianpoker.service;

import indianpoker.domain.humanplayer.Picture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ImageService {

    private String folderPath;

    @Value("${file.upload-dir}")
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    private String makeFullPath(String fileName) {
        return folderPath + "/" + fileName;
    }

    public Picture uploadPic(MultipartFile pic) throws Exception {
        if (pic == null || pic.isEmpty()) {
            return Picture.DEFAULT_PICTURE;
        }
        return uploading(pic);
    }

    private Picture uploading(MultipartFile pic) throws Exception {
        Picture uploadPicture = new Picture(pic.getOriginalFilename());
        File destinationFile;
        do {
            destinationFile = new File(makeFullPath(uploadPicture.getFileIdWithExtension()));
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        pic.transferTo(destinationFile);

        return uploadPicture;
    }

    public File getImageFile(String fileName) {
        return new File(makeFullPath(fileName));
    }

}
