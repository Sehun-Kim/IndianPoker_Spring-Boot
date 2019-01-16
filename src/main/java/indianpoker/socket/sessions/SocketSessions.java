package indianpoker.socket.sessions;

import indianpoker.dto.PlayerEnterInfoDto;
import indianpoker.dto.PlayerInfoDto;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

import static indianpoker.socket.util.SocketSessionUtil.playerFromSession;

public class SocketSessions {
    private Map<Long, GameSession> gameSessions;

    public SocketSessions() {
        gameSessions = new HashMap<>();
    }

    public PlayerEnterInfoDto addSession(long gameId, WebSocketSession webSocketSession) {
        GameSession gameSession = findByGameId(gameId);
        gameSessions.put(gameId, gameSession.addSession(webSocketSession));

        PlayerInfoDto playerInfoDto = playerFromSession(webSocketSession).toDto();
        return new PlayerEnterInfoDto(playerInfoDto);
    }

    public GameSession findByGameId(long gameId) {
        if (gameSessions.containsKey(gameId)) {
           return gameSessions.get(gameId);
        }
        return new GameSession(gameId);
    }


}
