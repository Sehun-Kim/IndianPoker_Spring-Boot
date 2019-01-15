package indianpoker.dto;

import indianpoker.dto.ex.PlayerInfoDto;
import indianpoker.vo.DtoType;

public class GameEnterInfoDto implements GameMessage {
    private static final String ENTER_MESSAGE = "님이 입장하셨습니다.";

    private String message;
    private PlayerInfoDto playerInfoDto;
    private DtoType type = DtoType.NOTICE;

    public GameEnterInfoDto() {
    }

    public GameEnterInfoDto(PlayerInfoDto playerInfoDto) {
        this.playerInfoDto = playerInfoDto;
        this.message = playerInfoDto.getName() + ENTER_MESSAGE;
    }

    @Override
    public DtoType getType() {
        return this.type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GameEnterInfoDto{" +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
