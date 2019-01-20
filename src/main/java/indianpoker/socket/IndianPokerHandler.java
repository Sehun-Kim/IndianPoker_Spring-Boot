package indianpoker.socket;

import indianpoker.dto.ReceiveMessageDto;
import indianpoker.service.MessageService;
import indianpoker.socket.sessions.GameSession;
import indianpoker.socket.sessions.SocketSessions;
import indianpoker.vo.MessageType;
import indianpoker.web.game.BettingController;
import indianpoker.web.game.GameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static indianpoker.socket.util.SocketSessionUtil.gameIdFromSession;

@Component
public class IndianPokerHandler extends TextWebSocketHandler {
    private SocketSessions socketSessions = new SocketSessions();

    @Autowired
    private GameController gameController;

    @Autowired
    private BettingController bettingController;

    @Autowired
    private MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(IndianPokerHandler.class);

    // Session이 접속하여 커넥션이 생길 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        long gameId = gameIdFromSession(session);

        // player Game 입장
        GameSession gameSession = socketSessions.findByGameId(gameId);
        gameController.initGame(gameSession, session);
    }

    // 메시지를 수신하였을 때 (클라이언트로 메세지 보내기)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("payload : {}", payload);

        long gameId = gameIdFromSession(session);

        ReceiveMessageDto receiveMessageDto = messageService.receiveMessage(message);
        MessageType receiveType = receiveMessageDto.getType();
        // turn 시작 요청
        if (receiveType.equals(MessageType.TURN_START)) {
            gameController.turnStart(socketSessions.findByGameId(gameId), receiveMessageDto.getValue());
        }

        // 배팅 요청
        if (!receiveType.equals(MessageType.TURN_START)) {
            bettingController.judgeCase(socketSessions.findByGameId(gameId), messageService.receiveMessage(message));
        }

        // 여기서 항시 게임 종료여부를 체크해야 함

        /*
         * 여기서 판단을 해야 함
         * NOTICE : 공지
         * TURNOVER : turn이 끝났다.
         * GAMEOVER : 게임이 끝났다.
         * GAME_INFO : 에러가 여기까지 오면 다시 입력하게 메세지를 보낼 것
         */

    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        long gameId = gameIdFromSession(session);
        GameSession gameSession = socketSessions.findByGameId(gameId);

        gameController.playerOut(gameSession, session);
    }
}
