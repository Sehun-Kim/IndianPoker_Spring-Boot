package indianpoker.domain.humanplayer;

import indianpoker.domain.game.player.AbstractPlayer;
import indianpoker.vo.Chips;
import support.domain.Deck;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class HumanPlayer extends AbstractPlayer {

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String password;

    @Embedded
    private Picture picture;

    public HumanPlayer() {
        super();
    }

    public HumanPlayer(String playerName, String password) {
        super(playerName);
        this.password = password;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWinCnt() {
        return this.winCnt;
    }

    public int getLoseCnt() {
        return this.loseCnt;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    // domain
    public HumanPlayer readyToGame(int playerChipsSize, boolean preemptive, Deck deck) {
        super.setChips(Chips.ofNumberOfChips(playerChipsSize));
        super.setFirstBetter(preemptive);
        super.setDeck(deck);
        return this;
    }

    public boolean matchPassword(String otherPassword) {
        return this.password.equals(otherPassword);
    }

    public boolean matchPlayerName(String name) {
        return getPlayerName().equals(name);
    }
}
