package support.fixture;

import indianpoker.domain.humanplayer.HumanPlayer;

public class PlayerFixture {
    private static final HumanPlayer DEFAULT_HUMAN_PLAYER = new HumanPlayer("tester1", "1234");
    private static final HumanPlayer SECOND_HUMAN_PLAYER = new HumanPlayer("tester2", "1234");

    public static HumanPlayer getDefaultHumanPlayer() {
        return DEFAULT_HUMAN_PLAYER;
    }

    public static HumanPlayer getSecondHumanPlayer() {
        return SECOND_HUMAN_PLAYER;
    }
}
