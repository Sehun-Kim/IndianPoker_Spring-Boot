package indianpoker.web;

import indianpoker.domain.user.User;
import indianpoker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/form")
    public String createForm() {
        return "user/join";
    }

    @PostMapping
    public String create(User user) {
        userService.add(user);
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/show";
    }

}
