package indianpoker.dto;

import indianpoker.vo.DtoType;

public class ErrorInfoDto implements GameMessage {

    private String playerName;
    private String message;
    private DtoType type;

    public ErrorInfoDto(String message, String playerName) {
        this.message = message;
        this.playerName = playerName;
        this.type = DtoType.ERROR;
    }

    public String getMessage() {
        return message;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public DtoType getType() {
        return null;
    }
}
