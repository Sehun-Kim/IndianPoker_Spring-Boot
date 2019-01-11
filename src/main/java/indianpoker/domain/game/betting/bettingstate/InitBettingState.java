package indianpoker.domain.game.betting.bettingstate;

import indianpoker.domain.game.player.Player;
import indianpoker.vo.BettingCase;
import indianpoker.vo.Chips;

public class InitBettingState extends AbstractBettingState {

    public InitBettingState(Chips chips, BettingCase bettingCase, Player player) {
        super(chips, bettingCase, player);
    }

    @Override
    public BettingState betting(Chips chips, BettingCase bettingCase) {
        if (isFirst()) return createFirstBettingState(chips, bettingCase);
        return createGeneralBettingState(chips, bettingCase);
    }
}
