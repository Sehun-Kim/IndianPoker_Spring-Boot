package support.util;

import indianpoker.domain.humanplayer.HumanPlayer;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

public class SessionUtil {
    public static final String PLAYER_SESSION = "loginedPlayer";
    public static final String GAME_ID = "gameId";

    public static Optional<HumanPlayer> getUserFromSession(NativeWebRequest webRequest) {
        return Optional.ofNullable((HumanPlayer) webRequest.getAttribute(PLAYER_SESSION, webRequest.SCOPE_SESSION));
    }

}
