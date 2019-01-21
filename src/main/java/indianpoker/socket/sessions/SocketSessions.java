package indianpoker.socket.sessions;

import java.util.HashMap;
import java.util.Map;

public class SocketSessions {
    private Map<Long, GameSession> gameSessions;

    public SocketSessions() {
        gameSessions = new HashMap<>();
    }

    public GameSession findByGameId(long gameId) {
        if (gameSessions.containsKey(gameId)) {
           return gameSessions.get(gameId);
        }
        gameSessions.put(gameId, new GameSession(gameId));
        return gameSessions.get(gameId);
    }
}
