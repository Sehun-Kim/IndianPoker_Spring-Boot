package indianpoker.web;

import indianpoker.domain.user.User;
import indianpoker.security.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/indianpoker")
public class IndianPokerController {
    @GetMapping("/start")
    public String start(@LoginUser User loginUser) {
        return "indianpoker/form";
    }
}
