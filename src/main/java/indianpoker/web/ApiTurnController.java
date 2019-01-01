package indianpoker.web;

import indianpoker.domain.user.User;
import indianpoker.security.LoginUser;
import indianpoker.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/turn/{turnId}")
public class ApiTurnController {

    @Autowired
    private TurnService turnService;

    @GetMapping("/init")
    public String init(@LoginUser User loginUser) {

        return "string";
    }

}
