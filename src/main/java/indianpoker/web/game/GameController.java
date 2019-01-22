package indianpoker.web.game;

import indianpoker.dto.ErrorInfoDto;
import indianpoker.dto.GameResultDto;
import indianpoker.exception.BankruptException;
import indianpoker.service.IndianPokerService;
import indianpoker.service.MessageService;
import indianpoker.socket.sessions.GameSession;
import indianpoker.vo.GameStatus;
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
        messageService.sendToAll(gameSession.addSession(webSocketSession), gameSession);
    }

    public void turnStart(GameSession gameSession, int turnCount) {
        try {
            if (!indianPokerService.isGameOver(gameSession.getGameId()))
                turnController.buildTurn(gameSession, turnCount);
            if (indianPokerService.isGameOver(gameSession.getGameId()))
                gameOver(gameSession);
        } catch (BankruptException e) {
            gameOver(gameSession);
        }
    }

    private void gameOver(GameSession gameSession) {
        GameResultDto gameResultDto = indianPokerService.judgeGameWinner(gameSession.getGameId());
        messageService.sendToAll(gameResultDto, gameSession);
    }

    public void playerOut(GameSession gameSession, WebSocketSession session) {
        ErrorInfoDto errorInfoDto = gameSession.removeSession(session);
        GameStatus gameStatus = indianPokerService.getGameStatus(gameSession.getGameId());

        // 정상적인 게임 종료 상황이 아닐 때 플레이어가 이탈했을 경우 에러 메세지를 보낸다.
        if (gameStatus.equals(GameStatus.IN_PROGRESS)) {
            indianPokerService.forceQuit(gameSession.getGameId());
            messageService.sendError(errorInfoDto, gameSession);
        }

        // 방을 만든 플레이어가 대기중에 나갔을 경우 게임 제거
        if (gameStatus.equals(GameStatus.WAITS_FOR_PLAYER)) {
            indianPokerService.remove(gameSession.getGameId());
        }
    }

}
