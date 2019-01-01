package indianpoker.vo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.BaseTest;

import static org.junit.Assert.*;

public class ChipsTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ChipsTest.class);

    @Test
    public void create_chips_success() {
        Chips chips = new Chips(0);
        logger.debug("chips : {}", chips);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_chips_fails() {
        new Chips(-1);
    }
}