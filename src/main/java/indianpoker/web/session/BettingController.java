package indianpoker.web.session;

import indianpoker.dto.RecieveMessageDto;
import indianpoker.service.IndianPokerService;
import indianpoker.service.MessageService;
import indianpoker.socket.sessions.GameSession;
import indianpoker.vo.BettingCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BettingController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private IndianPokerService indianPokerService;

    public void judgeCase(GameSession gameSession, RecieveMessageDto recieveMessageDto) {
        BettingCase bettingCase = BettingCase.valueOf(recieveMessageDto.getValue());

        if (bettingCase.equals(BettingCase.CALL_CASE))
            callBettingCase(gameSession);

        if (bettingCase.equals(BettingCase.RAISE_CASE)) {

        }

        if (bettingCase.equals(BettingCase.DIE_CASE)) {

        }
    }


    public void callBettingCase(GameSession gameSession) {

    }

    public void raiseBettingCase(GameSession gameSession, RecieveMessageDto recieveMessageDto) {

    }

    public void dieBettingCase(GameSession gameSession) {

    }
}
