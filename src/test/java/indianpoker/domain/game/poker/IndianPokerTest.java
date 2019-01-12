package indianpoker.domain.game.poker;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.vo.GameStatus;
import org.junit.Test;
import support.fixture.PlayerFixture;
import support.test.BaseTest;

public class IndianPokerTest extends BaseTest {
    HumanPlayer player1 = PlayerFixture.getDefaultHumanPlayer();

    @Test
    public void game_create() {
        IndianPoker indianPoker = new IndianPoker(player1, 20, "TRUE");
        softly.assertThat(indianPoker.getFirstPlayer().isFirst()).isTrue();
        softly.assertThat(indianPoker.getGameStatus()).isEqualTo(GameStatus.WAITS_FOR_PLAYER);
        softly.assertThat(indianPoker.getPlayerChipsSize()).isEqualTo(20);
        softly.assertThat(indianPoker.isPreemptive()).isTrue();
    }
}