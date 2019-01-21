package support.util;

import indianpoker.domain.game.betting.BettingTable;
import indianpoker.domain.game.betting.bettingstate.BettingState;

public class ChipExtractorUtil {
    public static void addBettingChips(BettingState bettingState, BettingTable bettingTable) {
        if (bettingState.isFirst())
            bettingTable.addFirst(bettingState.getChips());
        if (!bettingState.isFirst())
            bettingTable.addLast(bettingState.getChips());
    }

    public static void addAllBettingChips(BettingState bettingState1, BettingState bettingState2, BettingTable bettingTable) {
        addBettingChips(bettingState1, bettingTable);
        addBettingChips(bettingState2, bettingTable);
    }
}
