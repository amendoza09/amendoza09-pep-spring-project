package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService (MessageRepository msgRepository) {
        this.messageRepository = msgRepository;
    }

    public ResponseEntity<?> createMessage(Message message) {
        if(message.getMessageText() == null || message.getMessageText().length() > 255 || message.getMessageText().isBlank()) {
            return ResponseEntity.badRequest().body("Message cannot be blank or exceed 255 characters");
        }

        Message newMessage = messageRepository.save(message);
        return ResponseEntity.ok(newMessage);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public ResponseEntity<?> getMessageById(int id) {
        Optional<Message> message = messageRepository.findById(id);

        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().body(null));
    }

    public ResponseEntity<?> deleteMessage(int id) {
        if(messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.ok("Message not found");
    }

    public ResponseEntity<?> updateMessage(int id, Message message) {
        Optional<Message> existing = messageRepository.findById(id);

        if(existing.isPresent() && message.getMessageText() != null && !message.getMessageText().isBlank()
        && message.getMessageText().length() <= 25) {
            Message updateMessage = existing.get();
            updateMessage.setMessageText(message.getMessageText());
            messageRepository.save(updateMessage);
            return ResponseEntity.ok(1);
        }

        return ResponseEntity.badRequest().body("Error updating message");
    }
    
    public List<Message> getMessagesFromUser(int id) {
        return messageRepository.findPostedBy(id);
    }
}
