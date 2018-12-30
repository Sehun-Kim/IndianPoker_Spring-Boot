package indianpoker.service;

import indianpoker.domain.player.Player;
import indianpoker.vo.Chips;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import support.domain.Deck;

import static support.util.BetterCheckerUtil.checkFirst;
import static support.util.BetterCheckerUtil.checkLast;

@Service
public class PlayerService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    public void initPlayers(Player loginPlayer, Player autoPlayer, int firstBetter, int chipsNum) {
        loginPlayer.initPlayer(new Deck(), new Chips(chipsNum), checkFirst(firstBetter));
        autoPlayer.initPlayer(new Deck(), new Chips(chipsNum), checkLast(firstBetter));

        logger.debug("loginPlayer : {}", loginPlayer);
        logger.debug("autoPlayer : {}", autoPlayer);
    }

}
