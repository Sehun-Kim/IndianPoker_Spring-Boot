package indianpoker.dto;

import indianpoker.vo.MessageType;

public class CannotEnterInfoDto implements GameMessage {
    private String message;
    private MessageType type;

    public CannotEnterInfoDto() {
        this.message = "잘 못된 게임에 접근하셨습니다.";
        this.type = MessageType.CANNOT_ENTER;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public MessageType getType() {
        return type;
    }
}
