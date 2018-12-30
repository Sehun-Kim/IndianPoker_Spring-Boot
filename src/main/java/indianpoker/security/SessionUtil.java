package indianpoker.security;

import indianpoker.domain.player.Player;
import indianpoker.domain.user.User;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

public class SessionUtil {
    public static final String USER_SESSION = "loginedUser";
    public static final String AUTO_PLAYER = "autoPlayer";

    public static Optional<User> getUserFromSession(NativeWebRequest webRequest) {
        return Optional.ofNullable((User) webRequest.getAttribute(USER_SESSION, webRequest.SCOPE_SESSION));
    }

    public static Optional<Player> getPlayerFromSession(NativeWebRequest webRequest) {
        return getUserFromSession(webRequest)
                .filter(user -> user instanceof Player)
                .map(user -> (Player) user);
    }

    public static Optional<Player> getAutoPlayerFromSession(NativeWebRequest webRequest) {
        return Optional.ofNullable((Player) webRequest.getAttribute(AUTO_PLAYER, webRequest.SCOPE_SESSION));
    }
}
