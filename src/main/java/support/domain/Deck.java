package support.domain;


import indianpoker.vo.Card;
import support.util.CardGenerateUtil;

import java.util.List;

public class Deck {
    private List<Card> deck;

    private Deck(List<Card> deck) {
        this.deck = deck;
    }

    public static Deck ofGenerateAuto() {
        return new Deck(CardGenerateUtil.generateShuffleCards());
    }

    public static Deck ofGenerateManual(List<Card> cards) {
        return new Deck(cards);
    }

    public Card drawACard() {
        if (deck.isEmpty()) throw new IllegalStateException("Deck is Empty");
        return this.deck.remove(0);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "deck=" + deck +
                '}';
    }
}
