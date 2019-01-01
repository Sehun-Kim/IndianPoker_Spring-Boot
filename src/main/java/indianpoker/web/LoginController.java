package indianpoker.web;

import indianpoker.service.UserService;
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
    private UserService userService;

    @GetMapping("/login")
    public String form() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        try {
            session.setAttribute(SessionUtil.USER_SESSION, userService.login(userId, password));
            return "redirect:/";
        } catch (UnAuthenticationException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionUtil.USER_SESSION);
        return "redirect:/";
    }

}
