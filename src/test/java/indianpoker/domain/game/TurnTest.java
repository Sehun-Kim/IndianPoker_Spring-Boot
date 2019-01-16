package indianpoker.domain.game;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.domain.poker.IndianPoker;
import indianpoker.dto.GameInfoDto;
import indianpoker.vo.Chips;
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
        turn = indianPoker.readyToPlayer(player1).readyToPlayer(player2).generateTurn();
        logger.debug("turn : {}", turn);
    }

    @Test
    public void generateBettingInfo() {
        GameInfoDto gameInfoDto = turn.generateGameInfoDto(1L);
        softly.assertThat(gameInfoDto.getCurrentTableDto().getOtherChips()).isEqualTo(Chips.ofNumberOfChips(1));
        logger.debug("otherCard : {}", gameInfoDto.getOtherPlayerCard());
        softly.assertThat(gameInfoDto.getOwnPlayerInfoDto().getRemainChips()).isEqualTo(Chips.ofNumberOfChips(19));
    }
}