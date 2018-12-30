package indianpoker.domain.player;

import indianpoker.domain.user.User;
import indianpoker.vo.Chips;
import support.domain.Deck;

import javax.persistence.Transient;


public class HumanPlayer extends User implements Player {
    @Transient // db에 반영되지 않는 필드
    private Chips chips;

    @Transient
    private Deck deck;

    @Transient
    private boolean firstBetter;

    public HumanPlayer(String userId, String password) {
        super(userId, password);
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
        return this.chips;
    }

    @Override
    public String toString() {
        return "HumanPlayer{" +
                "playerId=" + this.getUserId() +
                "chips=" + chips +
                ", deck=" + deck +
                ", firstBetter=" + firstBetter +
                '}';
    }
}
