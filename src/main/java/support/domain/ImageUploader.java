package support.domain;

import indianpoker.domain.user.Picture;
import indianpoker.exception.NotImageDataException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
public class ImageUploader {
    private static String FILE_EXTENSION_FORMAT = "^((jpg|png|jpeg)$)";

    private String folderPath;

    @Value("${file.upload-dir}")
    void setUploadFileDir(final String uploadFileDir) throws Exception {
        this.folderPath = new ClassPathResource(uploadFileDir).getFile().getPath();
    }

    public Picture uploadPic(MultipartFile pic) throws Exception {
        if (pic == null || pic.isEmpty()) {
            return Picture.getDefaultPicture(folderPath);
        }
        return uploading(pic);
    }

    private Picture uploading(MultipartFile pic) throws Exception {
        File destinationFile;
        String fileName;
        do {
            fileName = makeFileName(pic.getOriginalFilename());
            destinationFile = new File(folderPath + "/" + fileName);
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        pic.transferTo(destinationFile);

        return new Picture(fileName, destinationFile.getPath());
    }

    boolean accept(String fileExtension) {
        return fileExtension.matches(FILE_EXTENSION_FORMAT);
    }

    String makeFileName(String fullFileName) {
        String fileExtension = FilenameUtils.getExtension(fullFileName).toLowerCase();
        String fileName = FilenameUtils.removeExtension(fullFileName);

        if (!accept(fileExtension))
            throw new NotImageDataException("NOT IMAGE FILE");

        return new StringBuilder(fileName)
                .append("_")
                .append(RandomStringUtils.randomAlphanumeric(10))
                .append(".")
                .append(fileExtension).toString();
    }
}
