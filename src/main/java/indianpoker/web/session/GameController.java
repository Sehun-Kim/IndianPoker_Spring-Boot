package indianpoker.web.session;

import indianpoker.dto.GameEnterInfoDto;
import indianpoker.service.IndianPokerService;
import indianpoker.service.MessageService;
import indianpoker.socket.sessions.GameSession;
import indianpoker.socket.sessions.SocketSessions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import static indianpoker.socket.util.SocketSessionUtil.gameIdFromSession;

@Component
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private IndianPokerService indianPokerService;

    @Autowired
    TurnController turnController;

    public void addPlayerSession(SocketSessions socketSessions, WebSocketSession session) {
        long gameId = gameIdFromSession(session);

        GameEnterInfoDto gameEnterInfoDto = socketSessions.addSession(gameId, session);
        logger.debug("gameEnterDto : {}", gameEnterInfoDto);
        GameSession gameSession = socketSessions.findByGameId(gameId);

        // 입장한 session들에게 공지
        messageService.sendMessage(gameEnterInfoDto, gameSession);
        if (gameSession.isStartable()) gameStart(gameSession);
    }

    public void gameStart(GameSession gameSession) {
        turnController.buildTurn(gameSession);
    }
}
