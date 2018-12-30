package indianpoker.domain.player;

import indianpoker.vo.Chips;
import support.domain.Deck;

public interface Player {
    Player initPlayer(Deck deck, Chips chips, boolean firstBetter);
    Chips showChips();
}
