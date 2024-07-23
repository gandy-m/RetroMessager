package messager.controller;


import lombok.RequiredArgsConstructor;
import messager.DTO.ChatDTO;
import messager.DTO.MessageDTO;
import messager.DTO.UserDTO;
import messager.mapper.ChatMapper;
import messager.mapper.MessageMapper;
import messager.mapper.UserMapper;
import messager.model.Chat;
import messager.model.Message;
import messager.model.User;
import messager.service.ChatService;
import messager.service.MessageService;
import messager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class MainController {


    private final MessageService messageServiceImpl;
    private final UserService userService;
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @GetMapping("/showChatList")
    public ResponseEntity<List<ChatDTO>> showChatList(Principal principal) {
        List<ChatDTO> chatDTOList = chatService.getChatDTOsByUsername(principal.getName());
        return ResponseEntity.ok(chatDTOList);
    }


    @PostMapping("/find/{username}")
    public ResponseEntity<UserDTO> findUser(@PathVariable String username) {
        User user;
        UserDTO userDTO;
        try {
            user = userService.find(username);
            userDTO = UserMapper.INSTANCE.userToUserDTO(user);
        } catch (Exception e) {
            return null;
        }
        return ResponseEntity.ok(userDTO);
    }


    @PostMapping("/addchat/{friendname}")
    public ResponseEntity<ChatDTO> addChat(@PathVariable String friendname, Principal principal) {
        Chat chat = chatService.addChat(principal.getName(), friendname);
        return ResponseEntity.ok(ChatMapper.INSTANCE.chatToChatDTO(chat));
    }


    @GetMapping("/openchat/{chatname}")
    public ResponseEntity<ChatDTO> getChatDTO(@PathVariable String chatname, Principal principal) {
        return ResponseEntity.ok(ChatMapper.INSTANCE.chatToChatDTO(chatService.getChat(principal.getName(), chatname)));
    }


    @MessageMapping("/sendMessage/{chatname}")
    public void addMessage(@DestinationVariable String chatname, String text, Principal principal) {
        System.out.println("addMessage");
        String recipientUsername;
        try {
            Chat chat = chatService.getChat(principal.getName(), chatname);
            if (chat.getUser1().getUsername().equals(principal.getName())) {
                recipientUsername = chat.getUser2().getUsername();
            } else {
                recipientUsername = chat.getUser1().getUsername();
            }
            Message message = messageServiceImpl.addMessage(text, principal.getName(), recipientUsername, chat.getId());
            MessageDTO messageDTO = MessageMapper.INSTANCE.messageToMessageDTO(message);
            String path = "/topic/chat/" + chat.getId();
            simpMessagingTemplate.convertAndSend(path, messageDTO);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);

        }
    }


    @PostMapping("/getUsername")
    public String getUsername(Principal principal) {
        return principal.getName();
    }

}