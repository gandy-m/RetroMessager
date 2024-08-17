package messager.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import messager.DTO.ChatDTO;
import messager.mapper.ChatMapper;
import messager.model.Chat;
import messager.model.User;
import messager.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {


    private final ChatRepository chatRepository;
    private final UserService userService;

    @Transactional
    @Override
    public Chat getChat(long chatId) {
        Chat chat = chatRepository.getChatById(chatId);
        return chat;
    }

    @Transactional
    @Override
    public Chat getChat(String username, String friendname) {
        return chatRepository.getChatByMembersNames(username, friendname);
    }

    @Transactional
    @Override
    public List<ChatDTO> getChatDTOsByUsername(String username) {
        List<Chat> chatList = chatRepository.getChatsByUsername(username);
        List<ChatDTO> chatDTOList = new ArrayList<>();
        for (Chat c : chatList) {
            chatDTOList.add(ChatMapper.INSTANCE.chatToChatDTO(c));
        }
        return chatDTOList;
    }


    @Transactional
    @Override
    public Chat addChat(String username, String friendname) {
        User user = userService.find(username);
        User friend = userService.find(friendname);
        Chat chat = new Chat(user, friend);
        chatRepository.save(chat);
        return chat;
    }

    @Transactional
    @Override
    public void removeChat(String username, String friendname) {
       Chat chat = chatRepository.getChatByMembersNames(username, friendname);
       chatRepository.delete(chat);
    }
}
