package messager.controller;

import lombok.RequiredArgsConstructor;
import messager.model.User;
import messager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class SettingsController {

    private final UserService userService;

    @PostMapping("/setImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            userService.setImage(file, principal.getName());
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");

        }
    }

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
}