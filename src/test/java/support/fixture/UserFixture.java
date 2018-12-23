package support.fixture;

import indianpoker.domain.user.User;

public class UserFixture {
    private static final User DEFAULT_USER = new User("tester", "1234");

    public static User getDefaultUser() {
        return DEFAULT_USER;
    }
}
