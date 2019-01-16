package indianpoker.dto;

import indianpoker.vo.DtoType;

public class RecieveMessageDto implements GameMessage {
    private long gameId;
    private int value;
    private String playerName;
    private DtoType type;

    public RecieveMessageDto() {
    }

    public RecieveMessageDto(long gameId, int value, String playerName, DtoType type) {
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
    public DtoType getType() {
        return type;
    }

    public void setType(DtoType type) {
        this.type = type;
    }
}
