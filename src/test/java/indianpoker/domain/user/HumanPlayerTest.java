package indianpoker.domain.user;

import indianpoker.domain.humanplayer.HumanPlayer;
import org.junit.Test;
import support.fixture.PlayerFixture;
import support.test.BaseTest;

public class HumanPlayerTest extends BaseTest {

    @Test
    public void matchPassword() {
        HumanPlayer test = PlayerFixture.getDefaultHumanPlayer();
        softly.assertThat(test.matchPassword("1234")).isTrue();
    }

}