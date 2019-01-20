package indianpoker.domain.game;

import indianpoker.domain.game.player.Player;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.vo.Card;
import org.junit.Before;
import org.junit.Test;
import support.domain.Deck;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DealerTest extends BaseTest {
    Dealer dealer;

    @Before
    public void setUp() throws Exception {
        dealer = new Dealer();
    }

    @Test
    public void getOtherPlayerCard() {
        List<Card> cards1 = new ArrayList<>();
        cards1.add(new Card(1));
        cards1.add(new Card(2));
        cards1.add(new Card(3));

        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(5));
        cards2.add(new Card(1));
        cards2.add(new Card(3));


        Player player1 = new HumanPlayer("tester1", "1").readyToGame(20, true, Deck.ofGenerateManual(cards1));
        Player player2 = new HumanPlayer("tester2", "1").readyToGame(20, false, Deck.ofGenerateManual(cards2));

        dealer.drawPlayerCards(player1, player2);
        Card card = dealer.getOtherPlayerCard(player1);
        softly.assertThat(player1).isEqualTo(player1);
        softly.assertThat(dealer.getPlayerCard(player1)).isEqualTo(new Card(1));
        softly.assertThat(card).isEqualTo(new Card(5));
    }
}