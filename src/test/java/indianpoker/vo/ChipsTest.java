package indianpoker.vo;

import indianpoker.exception.EmptyChipException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.BaseTest;

public class ChipsTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ChipsTest.class);

    @Test
    public void create_chips_success() {
        Chips chips = Chips.ofNumberOfChips(0);
        logger.debug("chips : {}", chips);
    }

    @Test(expected = EmptyChipException.class)
    public void create_chips_fails() {
        Chips.ofNumberOfChips(-1);
    }
}