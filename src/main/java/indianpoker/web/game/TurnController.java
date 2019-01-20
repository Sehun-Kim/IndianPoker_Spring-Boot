package indianpoker.web.game;

import indianpoker.dto.GameMessage;
import indianpoker.dto.TurnStartInfoDto;
import indianpoker.exception.EmptyChipException;
import indianpoker.service.IndianPokerService;
import indianpoker.service.MessageService;
import indianpoker.socket.sessions.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TurnController {
    private static final Logger logger = LoggerFactory.getLogger(TurnController.class);
    
    @Autowired
    private MessageService messageService;

    @Autowired
    private IndianPokerService indianPokerService;

    public void buildTurn(GameSession gameSession, int turnCount) {
        try {
            TurnStartInfoDto turnStartInfoDto = indianPokerService.generateTurn(gameSession.getGameId(), turnCount);
            logger.debug("turnStartInfo : {}", turnStartInfoDto);
            messageService.sendMessage(turnStartInfoDto, gameSession);

            runTurn(gameSession);
        } catch (EmptyChipException e) { // 칩을 하나씩 빼고 둘 중 한명이 칩이 바닥나면 그 턴은 바로 승패를 판단한다.
            GameMessage gameMessage = indianPokerService.judgeCallCase(gameSession.getGameId());
            messageService.sendMessage(gameMessage, gameSession);
        }
    }

    public void runTurn(GameSession gameSession) {
        //  turninfo player들한테 보내줌 (first 배팅인지 아닌지 알아서 바꿈)
        GameMessage gameMessage = indianPokerService.generateGameInfo(gameSession.getGameId());
        indianPokerService.turnMakeNotFirst(gameSession.getGameId());

        messageService.sendMessage(gameMessage, gameSession);
    }

}
