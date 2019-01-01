package indianpoker.service;

import indianpoker.domain.Turn;
import indianpoker.domain.player.AutoComPlayer;
import indianpoker.domain.player.HumanPlayer;
import indianpoker.domain.user.User;
import indianpoker.vo.Chips;
import org.springframework.stereotype.Service;
import support.domain.Deck;
import support.util.BetterCheckerUtil;

import javax.annotation.Resource;

@Service
public class TurnService {

    @Resource(name = "turn")
    private Turn turn;

    public Turn initTurnAndPlayers(User loginUser, int firstBetter, int chipsNum) {
        return turn.initTurnCount().initHumanPlayers(
                new HumanPlayer(
                        loginUser.getUserId(),
                        Deck.ofGenerateAuto(),
                        new Chips(chipsNum),
                        BetterCheckerUtil.checkFirst(firstBetter))).initAutoPlayer(
                new AutoComPlayer(
                        "autoPlayer",
                        Deck.ofGenerateAuto(),
                        new Chips(chipsNum),
                        BetterCheckerUtil.checkLast(firstBetter)
                ));
    }
}
