package senla.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import senla.model.User;
import senla.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends HttpServlet {

    private final UserService userService;

    @PostMapping
    public String createUser(@ModelAttribute @Valid User user) {
        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/user")
    public String getUser(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "users";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute @Valid User user) {
        userService.updateById(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}



