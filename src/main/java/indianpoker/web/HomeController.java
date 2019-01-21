package indianpoker.web;

import indianpoker.service.HumanPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private HumanPlayerService humanPlayerService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("top10Users", humanPlayerService.findTop10User());
        return "index";
    }
}
