package indianpoker.dto;

import indianpoker.vo.MessageType;

public class TurnInfoDto implements GameMessage {
    private PlayerInfoDto ownPlayerInfoDto;
    private PlayerInfoDto otherPlayerInfoDto;
    private MessageType type;

    public TurnInfoDto(PlayerInfoDto ownPlayerInfoDto, PlayerInfoDto otherPlayerInfoDto) {
        this.ownPlayerInfoDto = ownPlayerInfoDto;
        this.otherPlayerInfoDto = otherPlayerInfoDto;
        this.type = MessageType.TURN_INFO;
    }

    public PlayerInfoDto getOwnPlayerInfoDto() {
        return ownPlayerInfoDto;
    }

    public PlayerInfoDto getOtherPlayerInfoDto() {
        return otherPlayerInfoDto;
    }

    @Override
    public MessageType getType() {
        return this.type;
    }
}
