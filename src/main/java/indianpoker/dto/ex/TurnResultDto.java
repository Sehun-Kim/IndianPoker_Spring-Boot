package indianpoker.dto.ex;

import indianpoker.dto.GameMessage;
import indianpoker.dto.PlayerInfoDto;
import indianpoker.vo.Chips;
import indianpoker.vo.DtoType;

import java.util.ArrayList;
import java.util.List;

public class TurnResultDto implements GameMessage {
    private List<PlayerInfoDto> winners;
    private Chips winningChips;
    private DtoType type;

    public static TurnResultDto of() {
        return new TurnResultDto();
    }

    private TurnResultDto() {
        this.winners = new ArrayList<>();
        this.type = DtoType.TURN_RESULT;
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

    @Override
    public DtoType getType() {
        return this.type;
    }

    public Chips getWinningChips() {
        return winningChips;
    }

    public boolean isDraw() {
        return this.winners.size() == 2;
    }

    @Override
    public String toString() {
        return "TurnResultDto{" +
                "winners=" + winners + "\n" +
                ", winningChips=" + winningChips +
                '}';
    }
}
