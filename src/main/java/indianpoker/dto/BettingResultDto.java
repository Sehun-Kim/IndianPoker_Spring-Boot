package indianpoker.dto;

import indianpoker.vo.BettingCase;
import indianpoker.vo.DtoType;

public class BettingResultDto implements GameMessage {
    private String playerName;
    private BettingCase bettingCase;
    private DtoType type;

    public BettingResultDto(String playerName, BettingCase bettingCase) {
        this.playerName = playerName;
        this.bettingCase = bettingCase;
        this.type = DtoType.BETTING_RESULT;
    }

    public String getPlayerName() {
        return playerName;
    }

    public BettingCase getBettingCase() {
        return bettingCase;
    }

    @Override
    public DtoType getType() {
        return this.type;
    }
}
