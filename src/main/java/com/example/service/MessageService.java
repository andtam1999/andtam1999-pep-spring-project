package com.example.service;

import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.exception.*;
import com.example.repository.MessageRepository;
import java.util.List;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountService accountService;

    public MessageService(MessageRepository msgRepo, AccountService acctService) {
        messageRepository = msgRepo;
        accountService = acctService;
    }

    public Message insertMessage(Message msg) throws InvalidInputException {
        String msgTxt = msg.getMessageText();
        if (msgTxt.isEmpty() || msgTxt.length() > 255) {
            throw new InvalidInputException("Invalid message body");
        }
        if (!accountService.checkAccountIdExists(msg.getPostedBy())) {
            throw new InvalidInputException("Invalid user ID");
        }
        return messageRepository.save(msg);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.getByMessageId(id);
    }

    public boolean deleteMessageById(int id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void updateMessageById(String msgTxt, int id) throws InvalidInputException {
        if (msgTxt.isEmpty() || msgTxt.length() > 255) {
            throw new InvalidInputException("Invalid message body");
        }
        if (!messageRepository.existsById(id)) {
            throw new InvalidInputException("Invalid message id");
        }
        Message msg = messageRepository.getByMessageId(id);
        msg.setMessageText(msgTxt);
        messageRepository.save(msg);
    }

    public List<Message> getAllMessagesByAccountId(int id) {
        return messageRepository.findAllByPostedBy(id);
    }
}
