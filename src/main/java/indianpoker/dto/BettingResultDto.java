package indianpoker.dto;

import indianpoker.vo.BettingCase;
import indianpoker.vo.MessageType;

public class BettingResultDto implements GameMessage {
    private String playerName;
    private BettingCase bettingCase;
    private MessageType type;

    public BettingResultDto(String playerName, BettingCase bettingCase) {
        this.playerName = playerName;
        this.bettingCase = bettingCase;
        this.type = MessageType.BETTING_RESULT;
    }

    public String getPlayerName() {
        return playerName;
    }

    public BettingCase getBettingCase() {
        return bettingCase;
    }

    @Override
    public MessageType getType() {
        return this.type;
    }
}
