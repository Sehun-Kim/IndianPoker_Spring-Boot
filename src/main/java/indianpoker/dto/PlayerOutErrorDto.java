package indianpoker.dto;

import indianpoker.vo.MessageType;

public class PlayerOutErrorDto implements GameMessage {
    private String playerName;
    private MessageType type;

    public PlayerOutErrorDto(String playerName) {
        this.playerName = playerName;
        this.type = MessageType.ERROR;
    }

    @Override
    public MessageType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "PlayerOutErrorDto{" +
                "playerName='" + playerName + '\'' +
                ", type=" + type +
                '}';
    }
}
