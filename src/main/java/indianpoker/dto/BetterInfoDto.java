package indianpoker.dto;

import indianpoker.dto.ex.BettingChipBoundaryDto;
import indianpoker.vo.Card;
import indianpoker.vo.DtoType;

public class BetterInfoDto implements GameMessage {
    private BettingTableDto currentTableDto;
    private Card otherPlayerCard;
    private BettingChipBoundaryDto bettingChipBoundaryDto;
    private boolean firstBetting;
    private DtoType type;

    public BetterInfoDto(BettingTableDto currentTableDto, Card otherPlayerCard, BettingChipBoundaryDto bettingChipBoundaryDto) {
        this.currentTableDto = currentTableDto;
        this.otherPlayerCard = otherPlayerCard;
        this.bettingChipBoundaryDto = bettingChipBoundaryDto;
        this.firstBetting = false;
        this.type = DtoType.BETTER_INFO;
    }

    public BettingTableDto getCurrentTableDto() {
        return currentTableDto;
    }

    public Card getOtherPlayerCard() {
        return otherPlayerCard;
    }

    public boolean isFirstBetting() {
        return firstBetting;
    }

    public BettingChipBoundaryDto getBettingChipBoundaryDto() {
        return bettingChipBoundaryDto;
    }

    public void setFirstBetting(boolean firstBetting) {
        this.firstBetting = firstBetting;
    }

    @Override
    public DtoType getType() {
        return this.type;
    }
}
