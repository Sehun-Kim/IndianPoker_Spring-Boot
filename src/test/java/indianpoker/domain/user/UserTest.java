package indianpoker.domain.user;

import org.junit.Test;
import support.fixture.UserFixture;
import support.test.BaseTest;

public class UserTest extends BaseTest {

    @Test
    public void matchPassword() {
        User test = UserFixture.getDefaultUser();
        softly.assertThat(test.matchPassword("1234")).isTrue();
    }

}