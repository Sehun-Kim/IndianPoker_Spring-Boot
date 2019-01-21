package indianpoker.domain.humanplayer;

import indianpoker.exception.NotImageDataException;
import org.junit.Test;
import support.test.BaseTest;

public class PictureTest extends BaseTest {

    @Test(expected = NotImageDataException.class)
    public void not_img_extension() {
        String fileName = "test.txt";
        new Picture(fileName);
    }
}