package indianpoker.web;

import indianpoker.domain.player.AutoComPlayer;
import indianpoker.domain.player.Player;
import indianpoker.security.AutoPlayer;
import indianpoker.security.LoginPlayer;
import indianpoker.security.SessionUtil;
import indianpoker.service.PlayerService;
import indianpoker.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/indianpoker")
public class IndianPokerController {
    private static final Logger logger = LoggerFactory.getLogger(IndianPokerController.class);

    @Autowired
    private PlayerService playerService;

    @GetMapping("/init")
    public String init(@LoginPlayer User loginUser, HttpSession session) {
        session.setAttribute(SessionUtil.AUTO_PLAYER, new AutoComPlayer("autoPlayer"));
        return "indianpoker/form";
    }

    @PostMapping("/start")
    public String start(@AutoPlayer Player autoPlayer, @LoginPlayer Player loginPlayer, int firstBetter, int chipsNum) {
        playerService.initPlayers(loginPlayer, autoPlayer, firstBetter, chipsNum);
        return "redirect:/api/turn/1/init";
    }
}
