package indianpoker.dto.ex;

import java.util.ArrayList;
import java.util.List;

public class GameResultDto {
    private List<String> winnerNames;

    public GameResultDto() {
        this.winnerNames = new ArrayList<>();
    }

    public void addWinnerName(String winnerName) {
        winnerNames.add(winnerName);
    }

    public boolean isDraw() {
        return this.winnerNames.size() == 2;
    }

    public String getWinner() {
        return this.winnerNames.get(0);
    }

}
