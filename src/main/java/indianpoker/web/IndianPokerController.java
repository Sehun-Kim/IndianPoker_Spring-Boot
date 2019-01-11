package indianpoker.web;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.security.LoginPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/indianpoker")
public class IndianPokerController {
    private static final Logger logger = LoggerFactory.getLogger(IndianPokerController.class);


    @GetMapping("/init")
    public String initForm(@LoginPlayer HumanPlayer loginHumanPlayer) {
        return "indianpoker/form";
    }

    @PostMapping("/start")
    public String start(@LoginPlayer HumanPlayer loginHumanPlayer, int firstBetter, int chipsNum) {
        return null;
    }
}
