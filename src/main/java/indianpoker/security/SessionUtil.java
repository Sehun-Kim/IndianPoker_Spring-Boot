package indianpoker.security;

import indianpoker.domain.user.User;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

public class SessionUtil {
    public static final String USER_SESSION = "loginedUser";

    public static User getUserFromSession(NativeWebRequest webRequest) {
        return (User) webRequest.getAttribute(USER_SESSION, webRequest.SCOPE_SESSION);
    }

}
