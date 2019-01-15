package support.fixture;

import indianpoker.domain.poker.IndianPoker;

public class IndianPokerFixture {
    private static IndianPoker DEFAULT_POKER = new IndianPoker(20, "TRUE");

    public static IndianPoker getDefaultPoker() {
        DEFAULT_POKER.setId(1L);
        return DEFAULT_POKER;
    }
}
