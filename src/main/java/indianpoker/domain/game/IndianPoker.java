package indianpoker.domain.game;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.vo.GameStatus;
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

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;
}
