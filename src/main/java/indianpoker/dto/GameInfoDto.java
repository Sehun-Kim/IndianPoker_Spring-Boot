package indianpoker.dto;

import indianpoker.vo.MessageType;


public class GameInfoDto implements GameMessage {
    private String betterName;
    private TurnInfoDto turnInfoDto;
    private BetterInfoDto betterInfoDto;
    private MessageType type;

    public GameInfoDto(String betterName, TurnInfoDto turnInfoDto, BetterInfoDto betterInfoDto) {
        this.betterName = betterName;
        this.turnInfoDto = turnInfoDto;
        this.betterInfoDto = betterInfoDto;
        this.type = MessageType.GAME_INFO;
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
    public MessageType getType() {
        return this.type;
    }
}
