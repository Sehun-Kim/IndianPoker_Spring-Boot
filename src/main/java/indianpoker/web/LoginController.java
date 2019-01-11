package indianpoker.web;

import indianpoker.service.HumanPlayerService;
import indianpoker.exception.UnAuthenticationException;
import support.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private HumanPlayerService humanPlayerService;

    @GetMapping("/login")
    public String form() {
        return "player/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        try {
            session.setAttribute(SessionUtil.PLAYER_SESSION, humanPlayerService.login(userId, password));
            return "redirect:/";
        } catch (UnAuthenticationException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionUtil.PLAYER_SESSION);
        return "redirect:/";
    }

}
