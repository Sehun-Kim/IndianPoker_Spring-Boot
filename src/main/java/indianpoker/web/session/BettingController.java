package indianpoker.web.session;

import indianpoker.dto.BettingResultDto;
import indianpoker.dto.ErrorInfoDto;
import indianpoker.dto.GameInfoDto;
import indianpoker.dto.ReceiveMessageDto;
import indianpoker.dto.ex.TurnResultDto;
import indianpoker.exception.CanNotCallCaseException;
import indianpoker.exception.CannotRaiseException;
import indianpoker.service.IndianPokerService;
import indianpoker.service.MessageService;
import indianpoker.socket.sessions.GameSession;
import indianpoker.vo.BettingCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BettingController {
    private static final Logger logger = LoggerFactory.getLogger(BettingController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private IndianPokerService indianPokerService;

    public void judgeCase(GameSession gameSession, ReceiveMessageDto receiveMessageDto) {
        BettingCase bettingCase = BettingCase.valueOf(receiveMessageDto.getType().toString() + "_CASE");
        sendBettingResult(gameSession, bettingCase, receiveMessageDto.getPlayerName());

        if (bettingCase.equals(BettingCase.CALL_CASE))
            callBettingCase(gameSession);

        if (bettingCase.equals(BettingCase.RAISE_CASE))
            raiseBettingCase(gameSession, receiveMessageDto.getValue());

        if (bettingCase.equals(BettingCase.DIE_CASE))
            dieBettingCase(gameSession);
    }

    public void sendBettingResult(GameSession gameSession, BettingCase bettingCase, String playerName) {
        messageService.sendMessage(new BettingResultDto(playerName, bettingCase), gameSession);
    }

    public void callBettingCase(GameSession gameSession) {
        try {
            TurnResultDto turnResultDto = indianPokerService.callBetting(gameSession.getGameId());
            logger.debug("CALL : {}", turnResultDto);

            messageService.sendMessage(turnResultDto, gameSession);
        } catch (CanNotCallCaseException e) {
            ErrorInfoDto errorInfoDto = new ErrorInfoDto(e.getMessage(), e.getPlayerName());
            messageService.sendMessage(errorInfoDto, gameSession);
        }
    }

    public void raiseBettingCase(GameSession gameSession, int numberOfChips) {
        logger.debug("RAISE, numberOfChips : {}", numberOfChips);
        try {
            GameInfoDto gameInfoDto = indianPokerService.raiseBetting(gameSession.getGameId(), numberOfChips);
            messageService.sendMessage(gameInfoDto, gameSession);
        } catch (CannotRaiseException e) {
            ErrorInfoDto errorInfoDto = new ErrorInfoDto(e.getMessage(), e.getPlayerName());
            messageService.sendMessage(errorInfoDto, gameSession);
        }
    }

    public void dieBettingCase(GameSession gameSession) {
        TurnResultDto turnResultDto = indianPokerService.dieBetting(gameSession.getGameId());
        messageService.sendMessage(turnResultDto, gameSession);
    }
}
