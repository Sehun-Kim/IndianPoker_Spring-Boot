package indianpoker.domain.poker;

import indianpoker.domain.humanplayer.HumanPlayer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.fixture.PlayerFixture;
import support.test.BaseTest;

public class IndianPokerTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(IndianPokerTest.class);

    HumanPlayer player1 = PlayerFixture.getDefaultHumanPlayer();
    HumanPlayer player2 = PlayerFixture.getSecondHumanPlayer();

    @Test
    public void game_create() {

    }


    @Test
    public void gameStart() {

    }
}