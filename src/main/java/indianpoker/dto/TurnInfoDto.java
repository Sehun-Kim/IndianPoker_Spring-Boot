package indianpoker.dto;

import indianpoker.vo.DtoType;

public class TurnInfoDto implements GameMessage {
    private PlayerInfoDto ownPlayerInfoDto;
    private PlayerInfoDto otherPlayerInfoDto;
    private DtoType type;

    public TurnInfoDto(PlayerInfoDto ownPlayerInfoDto, PlayerInfoDto otherPlayerInfoDto) {
        this.ownPlayerInfoDto = ownPlayerInfoDto;
        this.otherPlayerInfoDto = otherPlayerInfoDto;
        this.type = DtoType.TURN_INFO;
    }

    public PlayerInfoDto getOwnPlayerInfoDto() {
        return ownPlayerInfoDto;
    }

    public PlayerInfoDto getOtherPlayerInfoDto() {
        return otherPlayerInfoDto;
    }

    @Override
    public DtoType getType() {
        return this.type;
    }
}
