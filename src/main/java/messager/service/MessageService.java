package messager.service;

import messager.DTO.MessageDTO;
import messager.model.Message;

import java.util.LinkedList;

public interface MessageService {

    Message addMessage(String text, String username, String recipientUsername, long chatId);
}
