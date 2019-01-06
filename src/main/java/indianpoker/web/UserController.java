package indianpoker.web;

import indianpoker.domain.user.User;
import indianpoker.service.UserService;
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
import support.domain.ImageUploader;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageUploader imageUploader;

    @GetMapping("/form")
    public String createForm() {
        return "user/join";
    }

    @PostMapping
    public String create(User user, MultipartFile pic) throws Exception {
        userService.add(user, imageUploader.uploadPic(pic));
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/show";
    }

}
