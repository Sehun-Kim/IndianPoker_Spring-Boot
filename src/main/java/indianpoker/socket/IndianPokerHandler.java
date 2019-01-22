package indianpoker.socket;

import indianpoker.dto.CannotEnterInfoDto;
import indianpoker.dto.ReceiveMessageDto;
import indianpoker.exception.CannotEnterGameException;
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
import static indianpoker.socket.util.SocketSessionUtil.playerFromSession;

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
        try {
            long gameId = gameIdFromSession(session);

            GameSession gameSession = socketSessions.findByGameId(gameId);
            gameController.initGame(gameSession, session);
        } catch (CannotEnterGameException e) {
            messageService.send(new CannotEnterInfoDto(), session);
        }
    }

    // 메시지를 수신하였을 때 (클라이언트로 메세지 보내기)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ReceiveMessageDto receiveMessageDto = messageService.receiveMessage(message);
        MessageType receiveType = receiveMessageDto.getType();
        try {
            long gameId = gameIdFromSession(session);

            if (receiveType.equals(MessageType.TURN_START))
                gameController.turnStart(socketSessions.findByGameId(gameId), receiveMessageDto.getValue());

            if (!receiveType.equals(MessageType.TURN_START))
                bettingController.judgeCase(socketSessions.findByGameId(gameId), messageService.receiveMessage(message));
        } catch (CannotEnterGameException e) {
            messageService.send(new CannotEnterInfoDto(), session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        try {
            long gameId = gameIdFromSession(session);
            GameSession gameSession = socketSessions.findByGameId(gameId);

            gameController.playerOut(gameSession, session);
        } catch (CannotEnterGameException e) {
            logger.info("{} is out", playerFromSession(session).getPlayerName());
        }
    }
}
