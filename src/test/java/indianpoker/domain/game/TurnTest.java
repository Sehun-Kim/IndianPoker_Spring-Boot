package indianpoker.domain.game;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.domain.poker.IndianPoker;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.fixture.IndianPokerFixture;
import support.fixture.PlayerFixture;
import support.test.BaseTest;

public class TurnTest extends BaseTest {
    IndianPoker indianPoker = IndianPokerFixture.getDefaultPoker();
    HumanPlayer player1 = PlayerFixture.getDefaultHumanPlayer();
    HumanPlayer player2 = PlayerFixture.getSecondHumanPlayer();

    private static final Logger logger = LoggerFactory.getLogger(TurnTest.class);

    private Turn turn;

    @Before
    public void setUp() throws Exception {
        indianPoker.readyToPlayer(player1).readyToPlayer(player2);
    }

    @Test
    public void isGameOver() {
        turn = indianPoker.generateTurn(20);
        softly.assertThat(turn.isGameOver()).isTrue();

    }
}