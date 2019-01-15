package indianpoker.socket.sessions;

import indianpoker.exception.CannotEnterGameException;
import org.junit.Test;
import org.springframework.web.socket.WebSocketSession;
import support.fixture.WebSocketSessionFixture;
import support.test.BaseTest;

public class GameSessionTest extends BaseTest {

    @Test
    public void add() {
        WebSocketSession webSocketSession = WebSocketSessionFixture.getDummySession();
        long gameId = 1L;

        GameSession gameSession = new GameSession(gameId);
        gameSession.addSession(webSocketSession);

        softly.assertThat(gameSession.getGameId()).isEqualTo(gameId);
        softly.assertThat(gameSession.isEmpty()).isFalse();
    }

    @Test(expected = CannotEnterGameException.class)
    public void add_with_exception() {
        WebSocketSession webSocketSession = WebSocketSessionFixture.getDummySession();
        long gameId = 1L;

        GameSession gameSession = new GameSession(gameId);
        gameSession.addSession(webSocketSession);
        gameSession.addSession(webSocketSession);
        gameSession.addSession(webSocketSession);
    }
}