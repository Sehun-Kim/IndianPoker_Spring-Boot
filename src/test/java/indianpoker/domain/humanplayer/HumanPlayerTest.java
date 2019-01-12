package indianpoker.domain.humanplayer;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.fixture.PlayerFixture;
import support.test.BaseTest;

import static org.junit.Assert.*;

public class HumanPlayerTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(HumanPlayerTest.class);
    @Test
    public void readyToGame() {
        HumanPlayer humanPlayer = PlayerFixture.getDefaultHumanPlayer();
        logger.debug("humanPlayer : {}", humanPlayer);
        humanPlayer.readyToGame(20, true);
        logger.debug("humanPlayer : {}", humanPlayer);
    }
}