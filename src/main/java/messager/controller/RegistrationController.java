package messager.controller;

import lombok.RequiredArgsConstructor;
import messager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestParam String username, @RequestParam String password) {
        try {
            userService.registration(username, password);
            return ResponseEntity.ok().body(Map.of("status", "success", "message", "Registration successful", "loginUrl", "/login"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

}
