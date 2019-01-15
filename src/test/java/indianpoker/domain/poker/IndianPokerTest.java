package indianpoker.domain.poker;

import indianpoker.domain.game.Turn;
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
        IndianPoker indianPoker = new IndianPoker(20, "TRUE");
        softly.assertThat(indianPoker.getPlayerChipsSize()).isEqualTo(20);
        softly.assertThat(indianPoker.isPreemptive()).isTrue();
    }


    @Test
    public void gameStart() {
        IndianPoker indianPoker = new IndianPoker(20, "TRUE");
        indianPoker.setId(1L);

        indianPoker.readyToPlayer(player1).readyToPlayer(player2);

        // turn의 정보와 어떤 플레이어가 betting을 할 것인지 정보를 준다.
        Turn turn = indianPoker.generateTurn();
        logger.debug("turn : {}", turn);
    }
}