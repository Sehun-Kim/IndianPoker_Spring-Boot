package indianpoker.socket.util;

import indianpoker.domain.humanplayer.HumanPlayer;
import org.springframework.web.socket.WebSocketSession;
import support.util.SessionUtil;

import java.util.Map;

public class SocketSessionUtil {
    private static Map<String, Object> getAttributes(WebSocketSession webSocketSession) {
        return webSocketSession.getAttributes();
    }

    public static HumanPlayer playerFromSession(WebSocketSession session) {
        Map<String, Object> httpSession = getAttributes(session);
        if(httpSession == null) {
            return new HumanPlayer();
        }
        return (HumanPlayer) httpSession.get(SessionUtil.PLAYER_SESSION);
    }

    public static long gameIdFromSession(WebSocketSession session) {
        Map<String, Object> httpSession = getAttributes(session);
        if(httpSession == null) {
            return 0L;
        }
        return (long) httpSession.get(SessionUtil.GAME_ID);
    }
}
