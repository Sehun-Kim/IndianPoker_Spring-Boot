package indianpoker.web;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.security.LoginPlayer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/turn/{turnId}")
public class ApiTurnController {

    @GetMapping("/init")
    public String init(@PathVariable int turnId, @LoginPlayer HumanPlayer loginHumanPlayer) {
        return "string";
    }

}
