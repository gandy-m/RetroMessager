package messager.service;

import messager.DTO.MessageDTO;
import messager.model.Message;

import java.util.LinkedList;

public interface MessageService {

    void addMessage(MessageDTO messageDTO);

    LinkedList<Message> getMessagesByChatId(long chatId);

    LinkedList<MessageDTO> getMessageDTOsByChatId(long chatId);

    Message addMessage(String text, String username, String recipientUsername, long chatId);
}
