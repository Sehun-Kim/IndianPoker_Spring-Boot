package indianpoker.domain.game.turn;

import indianpoker.domain.game.betting.BettingTable;
import indianpoker.domain.game.dealer.Dealer;
import indianpoker.domain.game.poker.IndianPoker;
import indianpoker.domain.humanplayer.HumanPlayer;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Turn extends AbstractEntity {
    public static final int FIRST_TURN_COUNT = 1;
    public static final int LAST_TURN_COUNT = 20;

    @ManyToOne
    @JoinColumn(name = "indian_poker_id", nullable = false, foreignKey = @ForeignKey(name = "fk_turn_to_indian_poker"))
    private IndianPoker indianPoker;

    @ManyToOne
    @JoinColumn(name = "first_player_id", nullable = false, foreignKey = @ForeignKey(name = "fk_turn_first_player"))
    private HumanPlayer firstPlayer;

    @ManyToOne
    @JoinColumn(name = "second_player_id", nullable = false, foreignKey = @ForeignKey(name = "fk_turn_second_player"))
    private HumanPlayer secondPlayer;

    @ManyToOne
    @JoinColumn(name = "dealer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_turn_dealer"))
    private Dealer dealer;


    @Column(name = "turn_count")
    private int turnCount = FIRST_TURN_COUNT;

    @Embedded
    private BettingTable bettingTable;



}
