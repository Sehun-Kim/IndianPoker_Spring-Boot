package indianpoker.domain.player;

import indianpoker.vo.Chips;
import support.domain.Deck;

public class AbstractPlayer implements Player {
    private String playerId;
    private Deck deck;
    private Chips chips;
    private boolean firstBetter;

    public AbstractPlayer(String playerId, Deck deck, Chips chips, boolean firstBetter) {
        this.playerId = playerId;
        this.deck = deck;
        this.chips = chips;
        this.firstBetter = firstBetter;
    }

    @Override
    public Chips showChips() {
        return this.chips;
    }
}
