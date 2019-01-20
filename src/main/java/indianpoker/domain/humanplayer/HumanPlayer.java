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

    @Column(nullable = false)
    private int winCnt = 0;

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

    public HumanPlayer readyToGame(int playerChipsSize, boolean preemptive, Deck deck) {
        super.setChips(Chips.ofNumberOfChips(playerChipsSize));
        super.setFirstBetter(preemptive);
        super.setDeck(deck);
        return this;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public HumanPlayer winGame() {
        this.winCnt++;
        return this;
    }

    public int getWinCnt() {
        return winCnt;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Picture getPicture() {
        return picture;
    }

    public boolean matchPassword(String otherPassword) {
        return this.password.equals(otherPassword);
    }

    public boolean matchPlayerName(String name) {
        return getPlayerName().equals(name);
    }
}
