package messager.service;


import messager.DTO.ChatDTO;
import messager.model.Chat;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ChatService {


   Chat getChat(long chatId);


   Chat getChat(String username, String friendname);


   Chat addChat(String username, String friendname);


   List<ChatDTO> getChatDTOsByUsername(String name);
}
