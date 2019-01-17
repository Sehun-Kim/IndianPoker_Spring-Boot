package indianpoker.socket.sessions;

import indianpoker.dto.PlayerEnterInfoDto;
import indianpoker.exception.CannotEnterGameException;
import indianpoker.exception.NonExistPlayerException;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

import static indianpoker.socket.util.SocketSessionUtil.playerFromSession;

public class GameSession {
    private static int GAME_SESSION_SIZE = 2;

    private Long gameId;
    private List<WebSocketSession> sessions;

    public GameSession(Long gameId) {
        this.gameId = gameId;
        this.sessions = new ArrayList<>();
    }

    public Long getGameId() {
        return gameId;
    }

    public PlayerEnterInfoDto addSession(WebSocketSession webSocketSession) {
        if (this.sessions.size() == GAME_SESSION_SIZE)
            throw new CannotEnterGameException();

        this.sessions.add(webSocketSession);
        return new PlayerEnterInfoDto(
                playerFromSession(webSocketSession).toDto(),
                sessions.size()
        );
    }

    public List<WebSocketSession> getAllSessions() {
        return this.sessions;
    }

    public boolean isStartable() {
        return this.sessions.size() == GAME_SESSION_SIZE;
    }

    public boolean isEmpty() {
        return this.sessions.isEmpty();
    }

    public WebSocketSession getPlayerSession(String name) {
        return sessions.stream()
                .filter(session -> playerFromSession(session).matchPlayerName(name))
                .findFirst()
                .orElseThrow(NonExistPlayerException::new);
    }
}
