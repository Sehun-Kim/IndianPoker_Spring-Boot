package indianpoker.domain.player;

import indianpoker.vo.Chips;
import support.domain.Deck;

public class AutoComPlayer implements Player {
    private String autoPlayerId;
    private Deck deck;
    private Chips chips;
    private boolean firstBetter;

    public AutoComPlayer(String autoPlayerId) {
        this.autoPlayerId = autoPlayerId;
    }

    @Override
    public Player initPlayer(Deck deck, Chips chips, boolean firstBetter) {
        this.deck = deck;
        this.chips = chips;
        this.firstBetter = firstBetter;
        return this;
    }

    @Override
    public Chips showChips() {
        return null;
    }

    @Override
    public String toString() {
        return "AutoComPlayer{" +
                "autoPlayerId='" + autoPlayerId + '\'' +
                ", deck=" + deck +
                ", chips=" + chips +
                ", firstBetter=" + firstBetter +
                '}';
    }
}
