package indianpoker.domain.game.player;

import indianpoker.domain.game.betting.bettingstate.BettingState;
import indianpoker.vo.BettingCase;
import indianpoker.vo.Card;
import indianpoker.vo.Chips;

public interface Player extends Winner, Loser, FirstJudgeable {
    Chips showChips();
    Chips payChips(Chips minusChips);
    BettingState initTurn();
    BettingState betting(Chips chips, BettingCase bettingCase);
    Card drawACard();
    boolean isGameOver();
    Player winGame();
    Player loseGame();
}
