package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidRequestException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            throw new InvalidRequestException("Message text cannot be blank");
        }
        if (message.getMessageText().length() > 255) {
            throw new InvalidRequestException("Message text cannot exceed 255 characters");
        }

        Optional<Account> account = accountRepository.findById(message.getPostedBy());
        if (account.isEmpty()) {
            throw new InvalidRequestException("User does not exist");
        }

        return messageRepository.save(message);
    }
    
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }
    
    public boolean deleteMessage(Integer id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean updateMessage(Integer id, String message) {
        if (message == null || message.isBlank() || message.length() > 255) {
            return false;
        }
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message newMessage = optionalMessage.get();
            newMessage.setMessageText(message);
            messageRepository.save(newMessage);
            return true;
        }
        return false;
    }
    
    public List<Message> getMessagesByUser(Integer id) {
        return messageRepository.findByPostedBy(id);
    }
}
