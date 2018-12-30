package indianpoker.vo;

import java.util.Objects;

public class Card implements Comparable<Card>{

    private static final int MIN = 1;
    private static final int MAX = 10;

    private int card;

    public Card(int card) {
        this.card = checkCard(card);
    }

    private int checkCard(int card){
        if(card < MIN || card > MAX) throw new IllegalArgumentException("Illegal argument for create card");
        return card;
    }

    @Override
    public int compareTo(Card other) {
        if(this.card > other.card) return 1;
        if(this.card < other.card) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card1 = (Card) o;
        return card == card1.card;
    }

    @Override
    public int hashCode() {
        return Objects.hash(card);
    }

    @Override
    public String toString() {
        return "Card{" +
                "card=" + card +
                '}';
    }
}
