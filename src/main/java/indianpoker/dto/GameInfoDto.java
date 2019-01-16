package indianpoker.dto;

import indianpoker.vo.DtoType;

public class GameInfoDto implements GameMessage {
    private String betterName;
    private TurnInfoDto turnInfoDto;
    private BetterInfoDto betterInfoDto;
    private DtoType type;

    public GameInfoDto(String betterName, TurnInfoDto turnInfoDto, BetterInfoDto betterInfoDto) {
        this.betterName = betterName;
        this.turnInfoDto = turnInfoDto;
        this.betterInfoDto = betterInfoDto;
        this.type = DtoType.GAME_INFO;
    }

    public GameInfoDto makeFirstBetting() {
        this.betterInfoDto.setFirstBetting(true);
        return this;
    }

    public String getBetterName() {
        return betterName;
    }

    public TurnInfoDto getTurnInfoDto() {
        return turnInfoDto;
    }

    public BetterInfoDto getBetterInfoDto() {
        return betterInfoDto;
    }

    @Override
    public DtoType getType() {
        return this.type;
    }
}
