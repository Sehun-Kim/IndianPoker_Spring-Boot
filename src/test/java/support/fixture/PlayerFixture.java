package support.fixture;

import indianpoker.domain.player.AutoComPlayer;
import indianpoker.domain.player.HumanPlayer;
import indianpoker.domain.player.Player;
import indianpoker.vo.Chips;
import support.domain.Deck;

public class PlayerFixture {
    private static final Player USER_PLAYER = new HumanPlayer("tester", Deck.ofGenerateAuto(), new Chips(20), true);
    private static final Player AUTO_PLAYER = new AutoComPlayer("autoPlayer", Deck.ofGenerateAuto(), new Chips(20), false);

    public static Player getUserPlayer() {
        return USER_PLAYER;
    }

    public static Player getAutoPlayer() {
        return AUTO_PLAYER;
    }
}
