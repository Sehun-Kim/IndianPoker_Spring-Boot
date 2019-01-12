package indianpoker.web;

import indianpoker.domain.game.poker.IndianPoker;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.security.LoginPlayer;
import indianpoker.service.IndianPokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/indianpokers")
public class IndianPokerController {
    private static final Logger logger = LoggerFactory.getLogger(IndianPokerController.class);

    @Autowired
    private IndianPokerService indianPokerService;

    @GetMapping
    public String gamelist(@LoginPlayer HumanPlayer loginPlayer, Model model) {
        logger.debug("loginPlayer : {}", loginPlayer);
        model.addAttribute("games", indianPokerService.findAllByGameWaits());
        return "indianpoker/list";
    }

    @GetMapping("/form")
    public String createForm(@LoginPlayer HumanPlayer loginPlayer) {
        return "indianpoker/form";
    }

    @PostMapping()
    public String create(@LoginPlayer HumanPlayer loginPlayer, String preemptive, int chipsSize) {
        logger.debug("loginPlayer : {}", loginPlayer);
        IndianPoker indianPoker = indianPokerService.createGame(loginPlayer, chipsSize, preemptive);
        logger.debug("loginPlayer : {}", loginPlayer);
        return "redirect:/indianpokers/" + indianPoker.getId();
    }

    @GetMapping("/{id}")
    public String start(@PathVariable("id") int indianPoker_id, @LoginPlayer HumanPlayer loginPlayer, HttpSession session) {
        session.setAttribute("gameId", indianPoker_id);
        return "indianpoker/game";
    }


}
