package support.fixture;

import indianpoker.domain.humanplayer.HumanPlayer;

public class PlayerFixture {
    private static final HumanPlayer DEFAULT_HUMAN_PLAYER = new HumanPlayer("tester", "1234");

    public static HumanPlayer getDefaultHumanPlayer() {
        return DEFAULT_HUMAN_PLAYER;
    }
}
