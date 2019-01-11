package indianpoker.web;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.service.HumanPlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import indianpoker.service.ImageService;

@Controller
@RequestMapping("/players")
public class HumanPlayerController {
    private static final Logger logger = LoggerFactory.getLogger(HumanPlayerController.class);

    @Autowired
    private HumanPlayerService humanPlayerService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/form")
    public String createForm() {
        return "player/join";
    }

    @PostMapping
    public String create(HumanPlayer humanPlayer, MultipartFile pic) throws Exception {
        humanPlayerService.add(humanPlayer, imageService.uploadPic(pic));
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("humanPlayer", humanPlayerService.findById(id));
        return "player/show";
    }

}
