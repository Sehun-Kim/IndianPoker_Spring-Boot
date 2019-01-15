package indianpoker.domain.poker;

import indianpoker.domain.game.Dealer;
import indianpoker.domain.game.Turn;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.exception.CannotEnterGameException;
import indianpoker.vo.GameStatus;
import indianpoker.vo.Preemptive;

import java.util.ArrayList;
import java.util.List;

public class IndianPoker {
    private static int MAX_PERSONNEL_SIZE = 2;

    private long id;

    private List<HumanPlayer> players;

    private Dealer dealer;

    private Turn turn;

    private int playerChipsSize;

    private boolean preemptive;

    private GameStatus gameStatus;

    public IndianPoker() {
        this.players = new ArrayList<>();
    }

    public IndianPoker(int playerChipsSize, String preemptive) {
        this.playerChipsSize = playerChipsSize;
        this.preemptive = Preemptive.valueOf(preemptive).isPreemptive();
        this.dealer = new Dealer();
        this.players = new ArrayList<>();
        this.gameStatus = GameStatus.WAITS_FOR_PLAYER;
    }

    public IndianPoker readyToPlayer(HumanPlayer player) {
        if (players.size() >= MAX_PERSONNEL_SIZE) throw new CannotEnterGameException("잘못된 게임에 접근하였습니다.");
        if (players.isEmpty()) {
            this.players.add(player.readyToGame(this.playerChipsSize, this.preemptive));
            return this;
        }
        this.players.add(player.readyToGame(this.playerChipsSize, !this.preemptive));
        this.gameStatus = GameStatus.IN_PROGRESS;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Turn getTurn() {
        return turn;
    }

    public int getPlayerChipsSize() {
        return playerChipsSize;
    }

    public boolean isPreemptive() {
        return preemptive;
    }

    public boolean isGameStatus(GameStatus gameStatus) {
        return this.gameStatus.equals(gameStatus);
    }

    public Turn generateTurn() {
        return this.turn = new Turn().addPlayers(this.players)
                .addDealer(this.dealer)
                .init();
    }


}
