package indianpoker.service;

import indianpoker.domain.game.Turn;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.domain.poker.IndianPoker;
import indianpoker.domain.poker.IndianPokerRepository;
import indianpoker.dto.GameInfoDto;
import indianpoker.dto.TurnResultDto;
import indianpoker.dto.TurnStartInfoDto;
import indianpoker.dto.GameResultDto;
import indianpoker.exception.CannotEnterGameException;
import indianpoker.exception.NonExistDataException;
import indianpoker.vo.Chips;
import indianpoker.vo.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndianPokerService {

    @Autowired
    private IndianPokerRepository indianPokerRepository;

    @Autowired
    private HumanPlayerService humanPlayerService;

    public List<IndianPoker> findAllByGameWaits() {
        return indianPokerRepository.findByGameStatus(GameStatus.WAITS_FOR_PLAYER);
    }

    public IndianPoker createGame(String gameName, int chipsNum, String preemptive) {
        return indianPokerRepository.save(new IndianPoker(gameName, chipsNum, preemptive));
    }

    public IndianPoker enterPlayer(long indianPoker_id, HumanPlayer loginPlayer) {
        return indianPokerRepository.findById(indianPoker_id)
                .map(i -> i.readyToPlayer(loginPlayer))
                .orElseThrow(CannotEnterGameException::new);
    }

    public TurnStartInfoDto generateTurn(long gameId, int turnCount) {
        return orderToRun(findByGameId(gameId).generateTurn(turnCount))
                .checkEmptyChipException()
                .generateTurnStartInfoDto();
    }

    private IndianPoker findByGameId(Long gameId) {
        return this.indianPokerRepository.findById(gameId).orElseThrow(NonExistDataException::new);
    }

    public Turn findTurnByGameId(Long gameId) {
        return findByGameId(gameId).getTurn();
    }

    public GameInfoDto generateGameInfo(Long gameId) {
        return findTurnByGameId(gameId).generateGameInfoDto();
    }

    private Turn orderToRun(Turn turn) {
        if (turn.firstPlayerIsFirst())
            return turn;
        return turn.reverse();
    }

    public void turnMakeNotFirst(Long gameId) {
        findTurnByGameId(gameId).makeNotFirst();
    }

    public TurnResultDto callBetting(Long gameId) {
        return findByGameId(gameId).getTurn().callBetting();
    }

    public TurnResultDto dieBetting(Long gameId) {
        return findByGameId(gameId).getTurn().dieBetting();
    }

    public GameInfoDto raiseBetting(Long gameId, int numberOfChips) {
        return findTurnByGameId(gameId).raiseBetting(Chips.ofNumberOfChips(numberOfChips));
    }

    public GameResultDto judgeGameWinner(Long gameId) {
        GameResultDto gameResultDto = findByGameId(gameId).judgeGameWinner();

        humanPlayerService.updateWinCnt(gameResultDto);
        return gameResultDto;
    }

    public boolean isGameOver(Long gameId) {
        Turn turn = findTurnByGameId(gameId);

        if (turn == null)
            return false;
        return turn.isGameOver();
    }

    public TurnResultDto judgeCallCase(Long gameId) {
        return findTurnByGameId(gameId).judgeCallCase();
    }

    public GameStatus getGameStatus(Long gameId) {
        return findByGameId(gameId).getGameStatus();
    }

    public IndianPoker forceQuit(Long gameId) {
        return findByGameId(gameId).forceQuit();
    }
}
