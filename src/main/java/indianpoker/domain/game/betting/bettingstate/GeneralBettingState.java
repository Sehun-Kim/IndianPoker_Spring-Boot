package indianpoker.domain.game.betting.bettingstate;

import indianpoker.domain.game.player.Player;
import indianpoker.vo.BettingCase;
import indianpoker.vo.Chips;

public class GeneralBettingState extends AbstractBettingState {


    public GeneralBettingState(Chips chips, BettingCase bettingCase, Player player) {
        super(chips, bettingCase, player);
    }

    @Override
    public BettingState betting(Chips chips, BettingCase bettingCase) {
        return createGeneralBettingState(chips, bettingCase);
    }
}
