package indianpoker.socket.sessions;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.socket.WebSocketSession;
import support.fixture.WebSocketSessionFixture;
import support.test.BaseTest;

import static org.junit.Assert.*;

public class SocketSessionsTest extends BaseTest {
    private SocketSessions socketSessions;

    @Before
    public void setUp() throws Exception {
        socketSessions = new SocketSessions();
    }

    @Test
    public void addSession_single() {
        long gameId = 1;
        WebSocketSession webSocketSession = WebSocketSessionFixture.getDummySession();
        socketSessions.addSession(gameId, webSocketSession);
        softly.assertThat(socketSessions.findByGameId(gameId).getAllSessions().size()).isEqualTo(1);
    }

    @Test
    public void addSession_double() {
        long gameId = 1;
        WebSocketSession webSocketSession = WebSocketSessionFixture.getDummySession();
        socketSessions.addSession(gameId, webSocketSession);
        socketSessions.addSession(gameId, webSocketSession);

        softly.assertThat(socketSessions.findByGameId(gameId).getAllSessions().size()).isEqualTo(2);
    }

}