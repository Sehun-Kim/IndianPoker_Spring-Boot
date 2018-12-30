package support.fixture;

import indianpoker.domain.player.AutoComPlayer;
import indianpoker.domain.player.Player;

public class PlayerFixture {
    private static final Player DEFAULT_PLAYER = UserFixture.getDefaultUser().toPlayer();
    private static final Player AUTO_PLAYER = new AutoComPlayer("autoPlayer");

    public static Player getAutoPlayer() {
        return AUTO_PLAYER;
    }

    public static Player getDefaultPlayer() {
        return DEFAULT_PLAYER;
    }
}
