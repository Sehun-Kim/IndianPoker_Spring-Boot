package indianpoker.socket;

import indianpoker.dto.PlayerEnterInfoDto;
import indianpoker.dto.RecieveMessageDto;
import indianpoker.service.MessageService;
import indianpoker.socket.sessions.GameSession;
import indianpoker.socket.sessions.SocketSessions;
import indianpoker.web.session.GameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    private MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(IndianPokerHandler.class);

    // Session이 접속하여 커넥션이 생길 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        long gameId = gameIdFromSession(session);

        // 접속한 Session을 저장
        PlayerEnterInfoDto playerEnterInfoDto = socketSessions.addSession(gameId, session);

        // 게임 시작
        gameController.initGame(socketSessions.findByGameId(gameId), playerEnterInfoDto);
    }

    // 메시지를 수신하였을 때 (클라이언트로 메세지 보내기)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("payload : {}", payload);

        long gameId = gameIdFromSession(session);
        GameSession gameSession = socketSessions.findByGameId(gameId);
        RecieveMessageDto recieveMessageDto = messageService.recieveMessage(message);


        /*
         * 여기서 판단을 해야 함
         * NOTICE : 공지
         * TURNOVER : turn이 끝났다.
         * GAMEOVER : 게임이 끝났다.
         * GAME_INFO : 에러가 여기까지 오면 다시 입력하게 메세지를 보낼 것
         */

    }


}
