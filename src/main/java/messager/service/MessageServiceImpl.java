package messager.service;

import lombok.RequiredArgsConstructor;
import messager.mapper.*;
import messager.DTO.MessageDTO;
import messager.model.*;
import messager.repository.MessageRepository;
import messager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {


    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatServiceImpl chatServiceImpl;


    @Override
    public Message addMessage(String text, String username, String recipientUsername, long chatId) {
        Optional<User> user = userRepository.findUserByUsername(username);
        Optional<User> recipient = userRepository.findUserByUsername(recipientUsername);
        Message message = new Message(text, user.get(), recipient.get(), chatServiceImpl.getChat(chatId));
        messageRepository.save(message);
        return message;
    }


}