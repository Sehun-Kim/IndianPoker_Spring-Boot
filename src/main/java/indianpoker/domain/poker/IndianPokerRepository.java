package indianpoker.domain.poker;

import indianpoker.vo.GameStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class IndianPokerRepository {
    private final Map<Long, IndianPoker> indianPokerMap;
    private long id = 0;

    public IndianPokerRepository() {
        this.indianPokerMap = new HashMap<>();
        // dummy
        ++this.id;
        IndianPoker indianPoker = new IndianPoker(20, "TRUE");
        indianPoker.setId(this.id);
        this.indianPokerMap.put(id, indianPoker);
    }

    public IndianPoker save(IndianPoker indianPoker) {
        indianPoker.setId(++this.id);
        this.indianPokerMap.put(this.id, indianPoker);
        return this.indianPokerMap.get(this.id);
    }

    public List<IndianPoker> findAll() {
        return indianPokerMap.values().stream()
                .collect(Collectors.toList());
    }

    public List<IndianPoker> findByGameStatus(GameStatus gameStatus) {
        return indianPokerMap.values().stream()
                .filter(i -> i.isGameStatus(gameStatus))
                .collect(Collectors.toList());
    }

    public Optional<IndianPoker> findById(long indianPoker_id) {
        return Optional.ofNullable(indianPokerMap.get(indianPoker_id));
    }
}
