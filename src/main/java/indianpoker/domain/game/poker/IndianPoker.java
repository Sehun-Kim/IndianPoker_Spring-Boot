package indianpoker.domain.game.poker;

import indianpoker.domain.game.dealer.Dealer;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.vo.GameStatus;
import indianpoker.vo.Preemptive;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class IndianPoker extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "first_player_id", nullable = false, foreignKey = @ForeignKey(name = "fk_indian_poker_first_player"))
    private HumanPlayer firstPlayer;

    @ManyToOne
    @JoinColumn(name = "second_player_id", nullable = true, foreignKey = @ForeignKey(name = "fk_indian_poker_second_player"))
    private HumanPlayer secondPlayer;

    @ManyToOne
    @JoinColumn(name = "dealer_id", nullable = true, foreignKey = @ForeignKey(name = "fk_indian_poker_dealer"))
    private Dealer dealer;

    @Column(nullable = false)
    private int playerChipsSize;

    @Column(nullable = false)
    private boolean preemptive;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private GameStatus gameStatus;

    public IndianPoker() {
    }

    public IndianPoker(HumanPlayer firstPlayer, int playerChipsSize, String preemptive) {
        this.playerChipsSize = playerChipsSize;
        this.preemptive = Preemptive.valueOf(preemptive).isPreemptive();
        this.gameStatus = GameStatus.WAITS_FOR_PLAYER;
        this.firstPlayer = readyToGame(firstPlayer, playerChipsSize, this.preemptive);
    }

    private HumanPlayer readyToGame(HumanPlayer player, int playerChipsSize, boolean preemptive) {
        return player.readyToGame(playerChipsSize, preemptive);
    }

    public HumanPlayer getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(HumanPlayer firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public HumanPlayer getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(HumanPlayer secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getPlayerChipsSize() {
        return playerChipsSize;
    }

    public boolean isPreemptive() {
        return preemptive;
    }
}
