package indianpoker.domain.user;

import indianpoker.domain.player.Player;
import org.junit.Test;
import support.fixture.UserFixture;
import support.test.BaseTest;

public class UserTest extends BaseTest {

    @Test
    public void matchPassword() {
        User test = UserFixture.getDefaultUser();
        softly.assertThat(test.matchPassword("1234")).isTrue();
    }

    @Test
    public void instanceOf() {
        User test = UserFixture.getDefaultUser().toPlayer();
        if (test instanceof Player)
            System.out.println("true");
    }
}