package indianpoker.web.session;

import indianpoker.dto.GameMessage;
import indianpoker.exception.EmptyChipException;
import indianpoker.service.IndianPokerService;
import indianpoker.service.MessageService;
import indianpoker.socket.sessions.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TurnController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private IndianPokerService indianPokerService;


    public void buildTurn(GameSession gameSession) {
        try {
            indianPokerService.generateTurn(gameSession.getGameId());
            runTurn(gameSession);
        } catch (EmptyChipException e) { // 칩을 하나씩 빼고 둘 중 한명이 칩이 바닥나면 그 턴은 바로 승패를 판단한다.
            GameMessage gameMessage = indianPokerService.callBetting(gameSession.getGameId());
            messageService.sendMessage(gameMessage, gameSession);
            // turn의 승패를 판단하고 어떤 플레이어가 칩이 없어서 게임이 종료될지 확인
            indianPokerService.checkBankrupt(gameSession.getGameId());
        }
    }

    // Turn 배팅할 때 메시지 보내는 부분
    public void runTurn(GameSession gameSession) {
        //  turninfo player들한테 보내줌 (first 배팅인지 아닌지 알아서 바꿈)
        GameMessage gameMessage = indianPokerService.generateGameInfo(gameSession.getGameId());
        indianPokerService.turnMakeNotFirst(gameSession.getGameId());

        messageService.sendMessage(gameMessage, gameSession);
    }

//    static TurnResultDto runTurn(Turn turn) {
//        ResultView.showBettingInfo(turn.generateGameInfoDto());
//        BettingCase bettingCase = InputView.inputBettingCase();
//
//        return BettingController.judgeCase(turn, bettingCase);
//    }


}
