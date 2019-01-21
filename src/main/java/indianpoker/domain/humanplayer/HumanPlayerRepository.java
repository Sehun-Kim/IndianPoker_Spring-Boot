package indianpoker.domain.humanplayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HumanPlayerRepository extends JpaRepository<HumanPlayer, Long> {
    Optional<HumanPlayer> findByPlayerName(String playerName);
    List<HumanPlayer> findTop10ByOrderByWinCntDesc();
}
