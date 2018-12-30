package indianpoker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

// session 마다 생성되는 bean
@Component("dealer")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Dealer {
    private static final Logger logger = LoggerFactory.getLogger(Dealer.class);

    // test용 dummy
    private int num;

    public Dealer() {
        logger.debug("Dealer : initialized!");
        this.num = 0;
    }

    public int getNum() {
        return num;
    }
}
