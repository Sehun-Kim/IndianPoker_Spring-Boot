package indianpoker.socket;

import indianpoker.socket.sessions.SocketSessions;
import indianpoker.web.session.GameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static indianpoker.socket.util.SocketSessionUtil.playerFromSession;

@Component
public class IndianPokerHandler extends TextWebSocketHandler {
    private SocketSessions socketSessions = new SocketSessions();

    @Autowired
    private GameController gameController;

    private static final Logger logger = LoggerFactory.getLogger(IndianPokerHandler.class);

    // Session이 접속하여 커넥션이 생길 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 어떤 Player가 입장했는지 나중에 지워도 된다.
        String playerName = playerFromSession(session).getPlayerName();
        logger.info("{} 접속", playerName);

        // WebSocketSession을 저장
        gameController.addPlayerSession(socketSessions, session);
    }

    // 메시지를 수신하였을 때 (클라이언트로 메세지 보내기)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("payload : {}", payload);

        /*
         * 여기서 판단을 해야 함
         * NOTICE : 공지
         * TURNOVER : turn이 끝났다.
         * GAMEOVER : 게임이 끝났다.
         * BETTING : 에러가 여기까지 오면 다시 입력하게 메세지를 보낼 것
         */


    }


}
