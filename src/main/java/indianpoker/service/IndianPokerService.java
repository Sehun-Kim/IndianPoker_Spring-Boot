package indianpoker.service;

import indianpoker.domain.game.Turn;
import indianpoker.domain.poker.IndianPoker;
import indianpoker.domain.poker.IndianPokerRepository;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.dto.GameInfoDto;
import indianpoker.exception.CannotEnterGameException;
import indianpoker.exception.NonExistDataException;
import indianpoker.vo.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndianPokerService {

    @Autowired
    private IndianPokerRepository indianPokerRepository;

    public List<IndianPoker> findAllByGameWaits() {
        return indianPokerRepository.findByGameStatus(GameStatus.WAITS_FOR_PLAYER);
    }

    public IndianPoker createGame(int chipsNum, String preemptive) {
        return indianPokerRepository.save(new IndianPoker(chipsNum, preemptive));
    }

    public IndianPoker enterPlayer(long indianPoker_id, HumanPlayer loginPlayer) {
        return indianPokerRepository.findById(indianPoker_id)
                .map(i -> i.readyToPlayer(loginPlayer))
                .orElseThrow(CannotEnterGameException::new);
    }

    public Turn generateTurn(long gameId) {
        return findByGameId(gameId).generateTurn();
    }

    private IndianPoker findByGameId(Long gameId) {
        return this.indianPokerRepository.findById(gameId).orElseThrow(NonExistDataException::new);
    }

    public Turn findTurnByGameId(Long gameId) {
        return findByGameId(gameId).getTurn();
    }

    public GameInfoDto turnFirstRun(Long gameId) {
        Turn turn = orderToRun(findTurnByGameId(gameId));
        turn.checkEmptyChipException();
        return turn.generateGameInfoDto().makeFirstBetting();
    }

    private Turn orderToRun(Turn turn) {
        if (turn.firstPlayerIsFirst())
            return turn;
        return turn.reverse();
    }
}
