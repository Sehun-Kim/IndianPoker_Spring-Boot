package indianpoker.service;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.domain.humanplayer.Picture;
import indianpoker.domain.humanplayer.HumanPlayerRepository;
import indianpoker.dto.GameResultDto;
import indianpoker.exception.NonExistDataException;
import indianpoker.exception.UnAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HumanPlayerService {
    private static final Logger logger = LoggerFactory.getLogger(HumanPlayerService.class);

    @Autowired
    private HumanPlayerRepository humanPlayerRepository;

    public List<HumanPlayer> findTop10User() {
        return humanPlayerRepository.findTop10ByOrderByWinCntDesc();
    }

    public HumanPlayer add(HumanPlayer humanPlayer, Picture picture) {
        logger.debug("picture name : {}", picture.getOriginalFileName());
        humanPlayer.setPicture(picture);
        return humanPlayerRepository.save(humanPlayer);
    }

    public HumanPlayer findById(Long playerId) {
        return humanPlayerRepository.findById(playerId).orElseThrow(NonExistDataException::new);
    }

    public HumanPlayer login(String playerName, String password) throws UnAuthenticationException {
        return humanPlayerRepository.findByPlayerName(playerName)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(UnAuthenticationException::new);
    }

    @Transactional
    public void updateWinCnt(GameResultDto gameResultDto) {
        for (String winnerName : gameResultDto.getWinnerNames()) {
            humanPlayerRepository.findByPlayerName(winnerName)
                    .map(player -> player.winGame())
                    .orElseThrow(NonExistDataException::new);
        }
    }
}
