package indianpoker.dto;

import indianpoker.vo.MessageType;

public class TurnStartInfoDto implements GameMessage {
    private int turnCount;
    private MessageType type;

    public TurnStartInfoDto(int turnCount) {
        this.turnCount = turnCount;
        this.type = MessageType.TURN_START;
    }

    public int getTurnCount() {
        return turnCount;
    }

    @Override
    public MessageType getType() {
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
