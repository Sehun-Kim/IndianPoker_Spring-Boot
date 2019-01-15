package indianpoker.web.session;

import indianpoker.dto.BettingInfoDto;
import indianpoker.exception.EmptyChipException;
import indianpoker.service.IndianPokerService;
import indianpoker.socket.sessions.GameSession;
import indianpoker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TurnController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private IndianPokerService indianPokerService;


    public void buildTurn(GameSession gameSession) {
        indianPokerService.generateTurn(gameSession.getGameId());
        start(gameSession);
    }

    public void start(GameSession gameSession) {
        BettingInfoDto bettingInfoDto;
        try {
            // turn의 첫 베팅 정보
            bettingInfoDto = indianPokerService.turnFirstRun(gameSession.getGameId());
            // session에 맞는 player에게 betting할 정보를 넘겨줘야 한다.
            messageService.sendMessage(bettingInfoDto, gameSession);
        } catch (EmptyChipException e) {
            // 칩을 하나씩 빼고 둘 중 한명이 칩이 바닥나면 그 턴은 바로 승패를 판단한다.
//            turnResultDto = turn.judgeCallCase();
            // session에 TurnResult를 모두 뿌린다.
        } finally {
//            ResultView.showTurnResult(turnResultDto);
//            turn.checkGameOver();
        }
    }

//    static TurnResultDto turnFirstRun(GameSession gameSession) {
//        turn.checkEmptyChipException();
//        return run(turn);
//    }
//
//    static TurnResultDto run(Turn turn) {
//        ResultView.showBettingInfo(turn.generateBettingInfo());
//        BettingCase bettingCase = InputView.inputBettingCase();
//
//        return BettingController.judgeCase(turn, bettingCase);
//    }
//
//    private  TurnResultDto orderToRun(GameSession gameSession) {
//        Turn turn = indianPokerService.findTurnByGameId(gameSession.getGameId());
//        if (turn.firstPlayerIsFirst())
//            return turnFirstRun(turn);
//        return turnFirstRun(turn.reverse());
//    }


}
