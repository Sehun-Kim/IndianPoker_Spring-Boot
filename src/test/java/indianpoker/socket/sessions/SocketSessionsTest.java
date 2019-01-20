package indianpoker.socket.sessions;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.socket.WebSocketSession;
import support.fixture.WebSocketSessionFixture;
import support.test.BaseTest;

public class SocketSessionsTest extends BaseTest {
    private SocketSessions socketSessions;

    @Before
    public void setUp() throws Exception {
        socketSessions = new SocketSessions();
        WebSocketSession webSocketSession = WebSocketSessionFixture.getDummySession();
        GameSession gameSession = socketSessions.findByGameId(1L);

        gameSession.addSession(webSocketSession);
    }

    @Test
    public void findByGameId_none() {
        long gameId = 2L;
        GameSession gameSession = socketSessions.findByGameId(gameId);

        softly.assertThat(gameSession.isEmpty()).isTrue();
    }

    @Test
    public void findByGameId_exist() {
        long gameId = 1L;
        GameSession gameSession = socketSessions.findByGameId(gameId);

        softly.assertThat(gameSession.isEmpty()).isFalse();
    }

}