package indianpoker.domain.poker;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.dto.GameResultDto;
import indianpoker.exception.CannotEnterGameException;
import indianpoker.vo.GameStatus;
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
    public void readyToPlayer_progress() {
        IndianPoker indianPoker = new IndianPoker("DUMMY", 20, "TRUE");
        indianPoker.readyToPlayer(player1).readyToPlayer(player2);
        softly.assertThat(indianPoker.getGameStatus()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    public void readyToPlayer_ready() {
        IndianPoker indianPoker = new IndianPoker("DUMMY", 20, "TRUE");
        indianPoker.readyToPlayer(player1);
        softly.assertThat(indianPoker.getGameStatus()).isEqualTo(GameStatus.WAITS_FOR_PLAYER);
    }

    @Test(expected = CannotEnterGameException.class)
    public void readyToPlayer_cannotEnter() {
        IndianPoker indianPoker = new IndianPoker("DUMMY", 20, "TRUE");
        indianPoker.readyToPlayer(player2).readyToPlayer(player1).readyToPlayer(player1);
    }

    @Test
    public void isGameStatus() {
        IndianPoker indianPoker = new IndianPoker("DUMMY", 20, "TRUE");
        indianPoker.readyToPlayer(player1).readyToPlayer(player2);

        softly.assertThat(indianPoker.isGameStatus(GameStatus.IN_PROGRESS)).isTrue();
    }

    @Test
    public void changeGameStatus_draw() {
        GameResultDto gameResultDto = new GameResultDto().addWinnerName("tester1").addWinnerName("tester2");

        IndianPoker indianPoker = new IndianPoker("DUMMY", 20, "TRUE");
        indianPoker.readyToPlayer(player1).readyToPlayer(player2);

        softly.assertThat(indianPoker.changeGameStatus(gameResultDto)).isEqualTo(GameStatus.TIE);
    }

    @Test
    public void changeGameStatus_firstPlayer_win() {
        GameResultDto gameResultDto = new GameResultDto().addWinnerName("tester1");

        IndianPoker indianPoker = new IndianPoker("DUMMY", 20, "TRUE");
        indianPoker.readyToPlayer(player1).readyToPlayer(player2);

        softly.assertThat(indianPoker.changeGameStatus(gameResultDto)).isEqualTo(GameStatus.FIRST_PLAYER_WON);
    }

    @Test
    public void forceQuit() {
        IndianPoker indianPoker = new IndianPoker("DUMMY", 20, "TRUE");
        indianPoker.readyToPlayer(player1).readyToPlayer(player2);

        softly.assertThat(indianPoker.forceQuit().getGameStatus()).isEqualTo(GameStatus.ERROR);
    }
}