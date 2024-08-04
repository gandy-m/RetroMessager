package messager.controller;


import lombok.RequiredArgsConstructor;
import messager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class PagesController {


    private final UserService userService;


    @GetMapping("/messager")
    public String retroMessager() {
        return "main";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
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