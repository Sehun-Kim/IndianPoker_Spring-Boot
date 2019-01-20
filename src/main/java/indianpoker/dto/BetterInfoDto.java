package indianpoker.dto;

import indianpoker.vo.Card;
import indianpoker.vo.MessageType;

public class BetterInfoDto implements GameMessage {
    private BettingTableDto currentTableDto;
    private Card otherPlayerCard;
    private BettingChipBoundaryDto bettingChipBoundaryDto;
    private boolean firstBetting;
    private boolean allIn;
    private MessageType type;

    public BetterInfoDto(BettingTableDto currentTableDto, Card otherPlayerCard, BettingChipBoundaryDto bettingChipBoundaryDto, boolean firstBetting, boolean allIn) {
        this.currentTableDto = currentTableDto;
        this.otherPlayerCard = otherPlayerCard;
        this.bettingChipBoundaryDto = bettingChipBoundaryDto;
        this.firstBetting = firstBetting;
        this.allIn = allIn;
        this.type = MessageType.BETTER_INFO;
    }

    public BettingTableDto getCurrentTableDto() {
        return currentTableDto;
    }

    public Card getOtherPlayerCard() {
        return otherPlayerCard;
    }

    public BettingChipBoundaryDto getBettingChipBoundaryDto() {
        return bettingChipBoundaryDto;
    }

    public boolean isFirstBetting() {
        return firstBetting;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    public boolean isAllIn() {
        return allIn;
    }
}
