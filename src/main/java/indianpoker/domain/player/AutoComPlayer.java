package indianpoker.domain.player;

import indianpoker.vo.Chips;
import support.domain.Deck;

public class AutoComPlayer extends AbstractPlayer {
    public AutoComPlayer(String playerId, Deck deck, Chips chips, boolean firstBetter) {
        super(playerId, deck, chips, firstBetter);
    }
}
