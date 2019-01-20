package indianpoker.dto;

import indianpoker.vo.MessageType;
import indianpoker.vo.Point;

public class ErrorInfoDto implements GameMessage {
    private String playerName;
    private String message;
    private Point point;
    private MessageType type;

    public ErrorInfoDto(String message, String playerName) {
        this.message = message;
        this.playerName = playerName;
        this.point = Point.BETTING;
        this.type = MessageType.ERROR;
    }

    public ErrorInfoDto playerOut() {
        this.point = Point.PLAYER_OUT;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public MessageType getType() {
        return this.type;
    }
}
