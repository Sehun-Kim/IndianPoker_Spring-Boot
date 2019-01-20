package indianpoker.domain.game.player;

import indianpoker.domain.game.betting.bettingstate.BettingState;
import indianpoker.domain.game.betting.bettingstate.InitBettingState;
import indianpoker.dto.PlayerInfoDto;
import indianpoker.vo.BettingCase;
import indianpoker.vo.Card;
import indianpoker.vo.Chips;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import support.domain.AbstractEntity;
import support.domain.Deck;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractPlayer extends AbstractEntity implements Player, Winner, Loser {
    @Size(min = 3, max = 20)
    @Column(unique = true, nullable = false)
    protected String playerName;

    @Transient
    private Deck deck;

    @Transient
    private Chips chips;

    @Transient
    private BettingState bettingState;

    @Transient
    private boolean firstBetter;

    public AbstractPlayer() {
    }

    public AbstractPlayer(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public Chips showChips() {
        return this.chips;
    }

    @Override
    public Chips payChips(Chips minusChips) {
        this.chips = this.chips.subChips(minusChips);
        return minusChips;
    }

    @Override
    public void gainChips(Chips chips) {
        this.chips = this.chips.addChips(chips);
    }

    @Override
    public BettingState betting(Chips chips, BettingCase bettingCase) {
        return this.bettingState = this.bettingState.betting(chips, bettingCase);
    }

    @Override
    public boolean isFirst() {
        return this.firstBetter;
    }

    @Override
    public BettingState initTurn() {
        return this.bettingState = new InitBettingState(this.payChips(Chips.ofNumberOfChips(1)), BettingCase.RAISE_CASE, this);
    }

    @Override
    public Card drawACard() {
        return deck.drawACard();
    }

    @Override
    public void changeLastBetter() {
        this.firstBetter = false;
    }

    @Override
    public void changeFirstBetter() {
        this.firstBetter = true;
    }

    @Override
    public PlayerInfoDto toDto() {
        return new PlayerInfoDto(this.playerName, this.chips);
    }

    @Override
    public boolean isGameOver() {
        return this.chips.isEmpty();
    }

    // protected method
    protected void setDeck(Deck deck) {
        this.deck = deck;
    }

    protected void setChips(Chips chips) {
        this.chips = chips;
    }

    protected void setFirstBetter(boolean firstBetter) {
        this.firstBetter = firstBetter;
    }

    @Override
    public String toString() {
        return "AbstractPlayer{" +
                "firstBetter=" + firstBetter +
                ", playerName='" + playerName + '\'' +
                ", deck=" + deck +
                ", chips=" + chips +
                ", bettingState=" + bettingState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        AbstractPlayer other = (AbstractPlayer) o;
        if (this.playerName.equals(other.playerName))
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }
}
