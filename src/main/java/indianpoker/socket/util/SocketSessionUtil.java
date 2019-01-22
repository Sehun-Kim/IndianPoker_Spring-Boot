package indianpoker.socket.util;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.exception.CannotEnterGameException;
import org.springframework.web.socket.WebSocketSession;
import support.util.SessionUtil;

import java.util.Map;
import java.util.Optional;

public class SocketSessionUtil {
    private static Map<String, Object> getAttributes(WebSocketSession webSocketSession) {
        return webSocketSession.getAttributes();
    }

    public static HumanPlayer playerFromSession(WebSocketSession session) {
        Map<String, Object> httpSession = getAttributes(session);
        try {
            return (HumanPlayer) httpSession.get(SessionUtil.PLAYER_SESSION);
        } catch (NullPointerException e) {
            throw new CannotEnterGameException();
        }
    }

    public static long gameIdFromSession(WebSocketSession session) {
        Map<String, Object> httpSession = getAttributes(session);
        try {
            return (long) httpSession.get(SessionUtil.GAME_ID);
        } catch (NullPointerException e) {
            throw new CannotEnterGameException();
        }
    }
}
