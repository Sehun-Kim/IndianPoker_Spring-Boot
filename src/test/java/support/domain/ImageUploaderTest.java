package support.domain;

import indianpoker.domain.user.Picture;
import indianpoker.exception.NotImageDataException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import support.test.BaseTest;

import java.io.File;
import java.io.FileInputStream;

public class ImageUploaderTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ImageUploaderTest.class);

    private ImageUploader imageUploader;

    @Before
    public void setUp() throws Exception {
        imageUploader = new ImageUploader();
        imageUploader.setUploadFileDir("static/uploads");
    }

    @Test
    public void extensionCheck() {
        String extension = "jpeg";
        softly.assertThat(imageUploader.accept(extension)).isTrue();
    }

    @Test(expected = NotImageDataException.class)
    public void makeFileName_not_image() {
        String fileName = "test.txt";
        imageUploader.makeFileName(fileName);
    }

    @Test
    public void makeFileName() {
        String fileName = "test.jpg";
        String result = imageUploader.makeFileName(fileName);
        softly.assertThat(result.split("_")[0]).isEqualTo(fileName.split("\\.")[0]);
        logger.debug("result : {}", result);
    }

    @Test
    public void uploadPic() throws Exception {
        String fileName = "example.jpg";
        File testImage = new ClassPathResource("static/uploads/example.jpeg").getFile();
        MultipartFile multipartFile = new MockMultipartFile(testImage.getPath(), fileName, null, new FileInputStream(testImage));
        logger.debug(multipartFile.getName());
        Picture picture = imageUploader.uploadPic(multipartFile);
        softly.assertThat(picture.getFileName()).startsWith("example");
    }

    @Test
    public void uploadPic_isEmpty() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("test", null, null, new byte[0]);
        logger.debug(multipartFile.getName());
        Picture picture = imageUploader.uploadPic(multipartFile);
        softly.assertThat(picture.getFileName()).isEqualTo("example.jpeg");
    }
}