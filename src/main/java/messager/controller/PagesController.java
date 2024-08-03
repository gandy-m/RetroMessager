package messager.controller;


import lombok.RequiredArgsConstructor;
import messager.service.UserService;
import messager.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class PagesController {


    private final UserService userService;


    @GetMapping("/messager")
    public String retroMessager() {
        return "springmessager";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String registration(@RequestParam String username, @RequestParam String password) {
        try {
            userService.registration(username, password);
        } catch (Exception e) {
            return "registrationFail";
        }
        return "registrationSuccess";
    }


    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }


    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "profile";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }
}