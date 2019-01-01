package support.util;

import indianpoker.domain.user.User;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

public class SessionUtil {
    public static final String USER_SESSION = "loginedUser";
    public static final String TURN = "turn";

    public static Optional<User> getUserFromSession(NativeWebRequest webRequest) {
        return Optional.ofNullable((User) webRequest.getAttribute(USER_SESSION, webRequest.SCOPE_SESSION));
    }

}
