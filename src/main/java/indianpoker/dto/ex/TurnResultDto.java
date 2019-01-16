package indianpoker.dto.ex;

import indianpoker.dto.PlayerInfoDto;
import indianpoker.vo.Chips;

import java.util.ArrayList;
import java.util.List;

public class TurnResultDto {
    private List<PlayerInfoDto> winners;
    private Chips winningChips;

    public static TurnResultDto of() {
        return new TurnResultDto();
    }

    private TurnResultDto() {
        this.winners = new ArrayList<>();
    }

    public TurnResultDto addWinner(PlayerInfoDto playerInfoDto) {
        this.winners.add(playerInfoDto);
        return this;
    }

    public TurnResultDto addWinningChips(Chips winningChips) {
        this.winningChips = winningChips;
        return this;
    }

    public List<PlayerInfoDto> getWinners() {
        return winners;
    }

    public boolean isDraw() {
        return this.winners.size() == 2;
    }

    public Chips getWinningChips() {
        return winningChips;
    }

    @Override
    public String toString() {
        return "TurnResultDto{" +
                "winners=" + winners + "\n" +
                ", winningChips=" + winningChips +
                '}';
    }
}
