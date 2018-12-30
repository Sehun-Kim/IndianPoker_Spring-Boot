package indianpoker.web;

import indianpoker.domain.Dealer;
import indianpoker.domain.player.Player;
import indianpoker.security.AutoPlayer;
import indianpoker.security.LoginPlayer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/turn/{turnId}")
public class ApiTurnController {

    @Resource(name = "dealer")
    private Dealer dealer;

    @GetMapping("/init")
    public String init(@AutoPlayer Player autoPlayer, @LoginPlayer Player loginPlayer) {
        System.out.println(dealer.getNum()); // test
        return "string";
    }

}
