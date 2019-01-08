package indianpoker.domain.user;

import indianpoker.exception.NotImageDataException;
import org.junit.Test;
import support.test.BaseTest;

import static org.junit.Assert.*;

public class PictureTest extends BaseTest {

    @Test(expected = NotImageDataException.class)
    public void not_img_extension() {
        String fileName = "test.txt";
        new Picture(fileName);
    }
}