package indianpoker.dto;

import indianpoker.vo.MessageType;

import java.util.ArrayList;
import java.util.List;

public class GameResultDto implements GameMessage {
    private List<String> winnerNames;
    private MessageType type;

    public GameResultDto() {
        this.winnerNames = new ArrayList<>();
        this.type = MessageType.GAME_RESULT;
    }

    public void addWinnerName(String winnerName) {
        winnerNames.add(winnerName);
    }

    public boolean isDraw() {
        return this.winnerNames.size() == 2;
    }

    public List<String> getWinnerNames() {
        return this.winnerNames;
    }

    @Override
    public MessageType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "GameResultDto{" +
                "winnerNames=" + winnerNames +
                ", type=" + type +
                '}';
    }
}
