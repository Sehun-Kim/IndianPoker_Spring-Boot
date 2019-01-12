package indianpoker.service;

import indianpoker.domain.game.poker.IndianPoker;
import indianpoker.domain.game.poker.IndianPokerRepository;
import indianpoker.domain.humanplayer.HumanPlayer;
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

    public IndianPoker createGame(HumanPlayer loginPlayer, int chipsNum, String preemptive) {
        return indianPokerRepository.save(new IndianPoker(loginPlayer, chipsNum, preemptive));
    }


}
