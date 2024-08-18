package messager.controller;


import lombok.RequiredArgsConstructor;
import messager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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


    @GetMapping("/profile/{username}")
    public String profile(Principal principal, @PathVariable String username) {
        if (userService.find(username) == null) {
            return "EmptyPage";
        }
        if (principal.getName().equals(username)) {
            return "profile";
        }
        return "readProfile";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }
}