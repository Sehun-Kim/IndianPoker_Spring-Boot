package indianpoker.dto;

import indianpoker.vo.Chips;
import indianpoker.vo.MessageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnResultDto implements GameMessage {
    private int turnCount;
    private Map<String, Integer> resultCards;
    private List<String> winnerNames;
    private Chips winningChips;
    private MessageType type;

    public static TurnResultDto of() {
        return new TurnResultDto();
    }

    private TurnResultDto() {
        this.winnerNames = new ArrayList<>();
        this.resultCards = new HashMap<>();
        this.type = MessageType.TURN_RESULT;
    }

    public TurnResultDto addWinner(String winnerName) {
        this.winnerNames.add(winnerName);
        return this;
    }

    public TurnResultDto addPlayerCards(Map<String, Integer> resultCards) {
        this.resultCards = resultCards;
        return this;
    }

    public TurnResultDto addWinningChips(Chips winningChips) {
        this.winningChips = winningChips;
        return this;
    }

    public TurnResultDto addTurnCount(int turnCount) {
        this.turnCount = turnCount;
        return this;
    }

    public List<String> getWinnerNames() {
        return winnerNames;
    }

    public Map<String, Integer> getResultCards() {
        return resultCards;
    }

    @Override
    public MessageType getType() {
        return this.type;
    }

    public Chips getWinningChips() {
        return winningChips;
    }

    public boolean isDraw() {
        return this.winnerNames.size() == 2;
    }

    public int getTurnCount() {
        return turnCount;
    }

    @Override
    public String toString() {
        return "TurnResultDto{" +
                "winnerNames=" + winnerNames + "\n" +
                ", winningChips=" + winningChips +
                '}';
    }
}
