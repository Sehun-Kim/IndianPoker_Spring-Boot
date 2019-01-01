package indianpoker.web;

import indianpoker.domain.Turn;
import indianpoker.domain.user.User;
import indianpoker.security.LoginUser;
import indianpoker.service.TurnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/indianpoker")
public class IndianPokerController {
    private static final Logger logger = LoggerFactory.getLogger(IndianPokerController.class);

    @Autowired
    private TurnService turnService;

    @GetMapping("/init")
    public String initForm(@LoginUser User loginUser) {
        return "indianpoker/form";
    }

    @PostMapping("/start")
    public String start(@LoginUser User loginUser, int firstBetter, int chipsNum) {
        Turn turn = turnService.initTurnAndPlayers(loginUser, firstBetter, chipsNum);
        return "redirect:/api/turn/" + turn.getTurnCount() + "/init";
    }
}
