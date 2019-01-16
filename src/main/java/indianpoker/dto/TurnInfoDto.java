package indianpoker.dto;

import indianpoker.vo.DtoType;

public class TurnInfoDto implements GameMessage {
    private int turnCount;
    private PlayerInfoDto ownPlayerInfoDto;
    private PlayerInfoDto otherPlayerInfoDto;
    private DtoType type;

    public TurnInfoDto(int turnCount, PlayerInfoDto ownPlayerInfoDto, PlayerInfoDto otherPlayerInfoDto) {
        this.turnCount = turnCount;
        this.ownPlayerInfoDto = ownPlayerInfoDto;
        this.otherPlayerInfoDto = otherPlayerInfoDto;
        this.type = DtoType.TURN_INFO;
    }

    public int getTurnCount() {
        return turnCount;
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
