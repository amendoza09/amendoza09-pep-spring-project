package com.example.service;

import com.example.entity.Message;

import java.util.List;

public interface MessageServiceInterface {

    List<Message> getAllMessages();

    Message getMessageById(Integer id);

    Message saveMessage(Message message);

    Message updateMessage(Integer id, Message message);

    void deleteMessage(Integer id);

    Message createMessage(Message message);

    int deleteMessageById(Integer messageId);

    List<Message> getAllMessagesForUser(Integer userId);
    
}
