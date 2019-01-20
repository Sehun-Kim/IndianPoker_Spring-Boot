package indianpoker.dto;

import indianpoker.vo.Chips;

public class BettingChipBoundaryDto {
    private Chips diffChips;
    private Chips betterChips;
    private Chips otherChips;

    public BettingChipBoundaryDto(Chips diffChips, Chips betterChips, Chips otherChips) {
        this.diffChips = diffChips;
        this.betterChips = betterChips;
        this.otherChips = otherChips;
    }

    public Chips getDiffChips() {
        return diffChips;
    }

    public Chips getBetterChips() {
        return betterChips;
    }

    public Chips getOtherChips() {
        return otherChips;
    }
}
