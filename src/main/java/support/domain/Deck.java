package support.domain;


import indianpoker.vo.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;

    public Deck() {
        this.deck = this.shuffle(this.init());
    }

    private List<Card> init(){
        List<Card> cards = new ArrayList<>();
        for (int i = 1; i <= 10; i++){
            cards.add(new Card(i));
            cards.add(new Card(i));
        }
        return cards;
    }

    public Card drawACard() {
        if (deck.isEmpty()) throw new IllegalStateException("Deck is Empty");
        return this.deck.remove(0);
    }

    public List<Card> shuffle(List<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "deckSize=" + deck.size() +
                '}';
    }
}
