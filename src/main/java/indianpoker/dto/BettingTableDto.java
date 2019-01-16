package indianpoker.dto;

import indianpoker.vo.Chips;

public class BettingTableDto {
    private Chips ownChips;
    private Chips otherChips;

    public BettingTableDto(Chips ownChips, Chips otherChips) {
        this.ownChips = ownChips;
        this.otherChips = otherChips;
    }

    public Chips getOwnChips() {
        return ownChips;
    }

    public Chips getOtherChips() {
        return otherChips;
    }

    @Override
    public String toString() {
        return "BettingTableDto{" +
                "ownChips=" + ownChips +
                ", otherChips=" + otherChips +
                '}';
    }
}
