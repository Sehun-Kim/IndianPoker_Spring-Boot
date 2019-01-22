package indianpoker.web;

import indianpoker.domain.poker.IndianPoker;
import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.exception.CannotEnterGameException;
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
import support.util.SessionUtil;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/indianpokers")
public class IndianPokerController {
    private static final Logger logger = LoggerFactory.getLogger(IndianPokerController.class);

    @Autowired
    private IndianPokerService indianPokerService;

    @GetMapping
    public String gamelist(@LoginPlayer HumanPlayer loginPlayer, Model model, HttpSession session) {
        session.removeAttribute(SessionUtil.GAME_ID);
        model.addAttribute("games", indianPokerService.findAllByGameWaits());
        return "indianpoker/list";
    }

    @GetMapping("/form")
    public String createForm(@LoginPlayer HumanPlayer loginPlayer) {
        return "indianpoker/form";
    }

    @PostMapping()
    public String create(@LoginPlayer HumanPlayer loginPlayer, String gameName, String preemptive, int chipsSize) {
        IndianPoker indianPoker = indianPokerService.createGame(gameName, chipsSize, preemptive);
        return "redirect:/indianpokers/" + indianPoker.getId();
    }

    @GetMapping("/{id}")
    public String gameRule(@PathVariable("id") long indianPoker_id, @LoginPlayer HumanPlayer loginPlayer, HttpSession session, Model model) {
        session.setAttribute(SessionUtil.GAME_ID, indianPoker_id);
        try {
            model.addAttribute("game", indianPokerService.enterPlayer(indianPoker_id, loginPlayer));
        } catch (CannotEnterGameException e) {
            return "redirect:/indianpokers";
        }
        return "indianpoker/rule";
    }

    @GetMapping("/{id}/start")
    public String start(@PathVariable("id") long indianPoker_id, @LoginPlayer HumanPlayer loginPlayer, Model model) {
        try {
            model.addAttribute("game", indianPokerService.findProgressGameByGameId(indianPoker_id, loginPlayer));
        } catch (CannotEnterGameException e) {
            return "redirect:/indianpokers";
        }
        return "indianpoker/game";
    }

}
