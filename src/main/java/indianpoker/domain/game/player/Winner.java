package indianpoker.domain.game.player;

import indianpoker.dto.ex.PlayerInfoDto;
import indianpoker.vo.Chips;

public interface Winner {
    void gainChips(Chips chips);
    void changeFirstBetter();
    PlayerInfoDto toDto();
}
