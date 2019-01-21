package indianpoker.dto;

import indianpoker.vo.MessageType;

public class ReceiveMessageDto implements GameMessage {
    private long gameId;
    private int value;
    private String playerName;
    private MessageType type;

    public ReceiveMessageDto() {
    }

    public ReceiveMessageDto(long gameId, int value, String playerName, MessageType type) {
        this.gameId = gameId;
        this.value = value;
        this.playerName = playerName;
        this.type = type;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
