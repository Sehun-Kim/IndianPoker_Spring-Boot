package indianpoker.web.session;

import indianpoker.exception.BankruptException;
import indianpoker.service.IndianPokerService;
import indianpoker.service.MessageService;
import indianpoker.socket.sessions.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private IndianPokerService indianPokerService;

    @Autowired
    TurnController turnController;

    public void initGame(GameSession gameSession, WebSocketSession webSocketSession) {
        // 입장한 session들에게 공지
        messageService.sendMessage(gameSession.addSession(webSocketSession), gameSession);
    }

    public void gameStart(GameSession gameSession) {
        try {
            // turn이 20턴 이하이면 buildTurn 실행
            if (!indianPokerService.isGameOver(gameSession.getGameId())) {
                turnController.buildTurn(gameSession);
            } // 게임할 수 없는 상황이면 그냥 아무런 메세지도 보내지 않는다.
        } catch (BankruptException e) {
            indianPokerService.judgeGameWinner(gameSession.getGameId());
            // todo
            // 게임 종료할 수 있는 조건으로 turn을 바꾸고 플레이어들에게 메세지를 보냄
        }
    }
}
