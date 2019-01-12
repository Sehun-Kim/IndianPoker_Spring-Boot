package indianpoker.domain.game.poker;


import indianpoker.vo.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndianPokerRepository extends JpaRepository<IndianPoker, Long> {
    List<IndianPoker> findByGameStatus(GameStatus gameStatus);
}
