package messager.controller;

import lombok.RequiredArgsConstructor;
import messager.model.Chat;
import messager.model.User;
import messager.service.ChatService;
import messager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final UserService userService;
    private final ChatService chatService;


    @GetMapping("/getImage")
    public ResponseEntity<byte[]> getImage(Principal principal) {
        User user = userService.find(principal.getName());
        if (user.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(user.getImage());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getImage/{username}")
    public ResponseEntity<byte[]> getImage(@PathVariable String username) {
        User user = userService.find(username);
        if (user.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(user.getImage());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/checkFriend/{username}")
    public ResponseEntity<Integer> checkFriend(@PathVariable String username, Principal principal) {
        Chat chat = chatService.getChat(username, principal.getName());
        if (chat == null) {
            return ResponseEntity.ok(0);
        }
        return ResponseEntity.ok(1);
    }

    @PostMapping("/addFriend/{friendname}")
    public ResponseEntity<Map<String, String>> addFriend(@PathVariable String friendname, Principal principal) {
        chatService.addChat(principal.getName(), friendname);
        return ResponseEntity.ok(Collections.singletonMap("message", "Add to friends successfully"));
    }

    @PostMapping("/removeFriend/{friendname}")
    public ResponseEntity<Map<String, String>> removeFriend(@PathVariable String friendname, Principal principal) {
        chatService.removeChat(principal.getName(), friendname);
        return ResponseEntity.ok(Collections.singletonMap("message", "Remove from friends successfully"));
    }
}
