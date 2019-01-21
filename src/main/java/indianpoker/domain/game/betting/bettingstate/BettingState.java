package indianpoker.domain.game.betting.bettingstate;

import indianpoker.vo.BettingCase;
import indianpoker.vo.Chips;

// 칩의 유효성은 콘트롤러에서 상대칩과 비교하는 것으로 해준다.
public interface BettingState {
    // 입력한 칩, 입력한 베팅케이스, 베팅하는 플레이어의 남은 칩 수(남은 칩 수로 idiot or close 가기위해)
    // 남은 칩수와 첫번째 시작하는 것 때문에 불린값이 필요해서 플레이어를 넘겨야 할 수 도 있다.
    BettingState betting(Chips chips, BettingCase bettingCase);
    Chips getChips();
    boolean isFirst();
}
