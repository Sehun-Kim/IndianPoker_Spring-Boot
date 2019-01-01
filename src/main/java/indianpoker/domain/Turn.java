package indianpoker.domain;

import indianpoker.domain.betting.BettingTable;
import indianpoker.domain.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


// session 마다 생성되는 bean
@Component("turn")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Turn {
    private static final Logger logger = LoggerFactory.getLogger(Turn.class);

    public static final int FIRST_TURN_COUNT = 1;
    public static final int LAST_TURN_COUNT = 20;

    private int turnCount;
    private Player humanPlayer;
    private Player autoPlayer;
    private Dealer dealer;
    private BettingTable bettingTable;

    public Turn() {
        logger.debug("Init Turn!!!!");
    }

    public Turn initTurnCount() {
        this.turnCount = FIRST_TURN_COUNT;
        return this;
    }

    public Turn initHumanPlayers(Player humanPlayer) {
        this.humanPlayer = humanPlayer;
        return this;
    }

    public Turn initAutoPlayer(Player autoPlayer) {
        this.autoPlayer = humanPlayer;
        return this;
    }

    public Turn initDealer(Dealer dealer) {
        this.dealer = dealer;
        return this;
    }

    public Turn initBettingTable(BettingTable bettingTable) {
        this.bettingTable = bettingTable;
        return this;
    }


    public int getTurnCount() {
        return turnCount;
    }

}
