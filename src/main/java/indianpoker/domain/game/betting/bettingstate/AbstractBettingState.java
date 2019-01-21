package indianpoker.domain.game.betting.bettingstate;

import indianpoker.domain.game.player.Player;
import indianpoker.vo.BettingCase;
import indianpoker.vo.Chips;

public abstract class AbstractBettingState implements BettingState {
    private Chips chips;
    private BettingCase bettingCase;
    private Player player;

    public AbstractBettingState(Chips chips, BettingCase bettingCase, Player player) {
        this.chips = chips;
        this.bettingCase = bettingCase;
        this.player = player;
    }

    public Chips getChips() {
        return chips;
    }

    protected BettingState createFirstBettingState(Chips chips, BettingCase bettingCase) {
        if (bettingCase.equals(BettingCase.DIE_CASE)) return createCloseBettingState(chips, bettingCase);
        return new FirstBettingState(chips, bettingCase, this.player);
    }

    protected BettingState createGeneralBettingState(Chips chips, BettingCase bettingCase) {
        if (isClose(bettingCase)) return createCloseBettingState(chips, bettingCase);
        return new GeneralBettingState(chips, bettingCase, this.player);
    }

    public boolean isFirst() {
        return player.isFirst();
    }

    private BettingState createCloseBettingState(Chips chips, BettingCase bettingCase) {
        return new CloseBettingState(chips, bettingCase, this.player);
    }

    private boolean isClose(BettingCase bettingCase) {
        return bettingCase.equals(BettingCase.CALL_CASE) || bettingCase.equals(BettingCase.DIE_CASE);
    }
}
