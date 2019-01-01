package support.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.BaseTest;
import support.util.CardGenerateUtil;

import static org.junit.Assert.*;

public class DeckTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(DeckTest.class);

    @Test
    public void deck_create_manual() {
        Deck deck = Deck.ofGenerateManual(CardGenerateUtil.generateCards());
        logger.debug("deck : {}", deck);
    }

    @Test
    public void deck_create_auto() {
        Deck deck = Deck.ofGenerateAuto();
        logger.debug("deck : {}", deck);
    }
}