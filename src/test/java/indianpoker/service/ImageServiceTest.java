package indianpoker.service;

import indianpoker.domain.humanplayer.Picture;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import support.test.BaseTest;

import java.io.File;
import java.io.FileInputStream;

public class ImageServiceTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ImageServiceTest.class);

    private ImageService imageService;

    private static final String folderPath = "/Users/sehun/Desktop/uploads";

    @Before
    public void setUp() throws Exception {
        imageService = new ImageService();
        imageService.setFolderPath(folderPath);
    }

    @Test
    public void uploadPic() throws Exception {
        String fileName = "example.jpg";
        File testImage = new File(folderPath + "/example.jpeg");
        MultipartFile multipartFile = new MockMultipartFile(testImage.getPath(), fileName, null, new FileInputStream(testImage));
        logger.debug(multipartFile.getName());
        Picture picture = imageService.uploadPic(multipartFile);
        softly.assertThat(picture.getOriginalFileName()).startsWith("example");
    }

    @Test
    public void uploadPic_isEmpty() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("test", null, null, new byte[0]);
        logger.debug(multipartFile.getName());
        Picture picture = imageService.uploadPic(multipartFile);
        softly.assertThat(picture.getFileIdWithExtension()).isEqualTo("example.jpeg");
    }

    @Test
    public void getImageFile() {
        String fileName = "example.jpeg";
        File image = imageService.getImageFile(fileName);
        softly.assertThat(image.getName()).isEqualTo(fileName);
    }
}