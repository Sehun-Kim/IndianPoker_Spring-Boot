package indianpoker.dto;

import indianpoker.dto.ex.BettingTableDto;
import indianpoker.vo.Card;
import indianpoker.vo.DtoType;

public class SingleBettingInfoDto implements GameMessage {
    private BettingTableDto currentTableDto;
    private Card otherPlayerCard;
    private boolean firstBetting;
    private DtoType type;

    public SingleBettingInfoDto(BettingTableDto currentTableDto, Card otherPlayerCard) {
        this.currentTableDto = currentTableDto;
        this.otherPlayerCard = otherPlayerCard;
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

    public void setFirstBetting(boolean firstBetting) {
        this.firstBetting = firstBetting;
    }

    @Override
    public DtoType getType() {
        return this.type;
    }
}
