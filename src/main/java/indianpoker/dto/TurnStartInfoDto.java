package indianpoker.dto;

import indianpoker.vo.DtoType;

public class TurnStartInfoDto implements GameMessage {
    private int turnCount;
    private DtoType type;

    public TurnStartInfoDto(int turnCount) {
        this.turnCount = turnCount;
        this.type = DtoType.TURN_START;
    }

    public int getTurnCount() {
        return turnCount;
    }

    @Override
    public DtoType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TurnStartInfoDto{" +
                "turnCount=" + turnCount +
                ", type=" + type +
                '}';
    }
}
