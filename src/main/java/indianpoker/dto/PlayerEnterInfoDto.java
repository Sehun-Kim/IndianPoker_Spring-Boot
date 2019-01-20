package indianpoker.dto;

import indianpoker.vo.MessageType;

public class PlayerEnterInfoDto implements GameMessage {
    private static final String ENTER_MESSAGE = "님이 입장하셨습니다.";

    private String message;
    private PlayerInfoDto playerInfoDto;
    private int numberOfPeople;
    private MessageType type = MessageType.NOTICE;


    public PlayerEnterInfoDto(PlayerInfoDto playerInfoDto, int numberOfPeople) {
        this.playerInfoDto = playerInfoDto;
        this.message = playerInfoDto.getName() + ENTER_MESSAGE;
        this.numberOfPeople = numberOfPeople;
    }

    public String getMessage() {
        return message;
    }

    public PlayerInfoDto getPlayerInfoDto() {
        return playerInfoDto;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "PlayerEnterInfoDto{" +
                "message='" + message + '\'' +
                ", playerInfoDto=" + playerInfoDto +
                ", numberOfPeople=" + numberOfPeople +
                ", type=" + type +
                '}';
    }
}
