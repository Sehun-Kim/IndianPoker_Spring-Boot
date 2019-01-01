package support.util;

import indianpoker.vo.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardGenerateUtil {
    public static List<Card> generateCards() {
        List<Card> cards = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            cards.add(new Card(i));
            cards.add(new Card(i));
        }
        return cards;
    }

    public static List<Card> generateShuffleCards() {
        List<Card> cards = generateCards();
        Collections.shuffle(cards);
        return cards;
    }
}
