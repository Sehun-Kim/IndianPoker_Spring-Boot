package indianpoker.domain.humanplayer;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.Deck;
import support.fixture.PlayerFixture;
import support.test.BaseTest;

public class HumanPlayerTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(HumanPlayerTest.class);

    HumanPlayer humanPlayer = PlayerFixture.getDefaultHumanPlayer();

    @Test
    public void readyToGame() {
        Deck deck = Deck.ofGenerateAuto();
        logger.debug("humanPlayer : {}", humanPlayer);
        humanPlayer.readyToGame(20, true, deck);
        logger.debug("humanPlayer : {}", humanPlayer);
    }

    @Test
    public void matchPassword() {
        HumanPlayer test = PlayerFixture.getDefaultHumanPlayer();
        softly.assertThat(test.matchPassword("1234")).isTrue();
    }

    @Test
    public void winGame() {
        humanPlayer.winGame();
        softly.assertThat(humanPlayer.getWinCnt()).isEqualTo(1);
    }
}