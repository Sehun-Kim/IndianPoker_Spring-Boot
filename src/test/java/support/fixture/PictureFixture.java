package support.fixture;

import indianpoker.domain.user.Picture;

public class PictureFixture {
    private static final Picture picture = new Picture();

    public static Picture getPicture() {
        return picture;
    }
}
