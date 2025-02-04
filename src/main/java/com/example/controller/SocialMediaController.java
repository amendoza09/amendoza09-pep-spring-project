package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    // register user
    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        Account newAccount = accountService.registerAccount(account);
        if (newAccount == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(newAccount);
    }

    // login user
    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account) {
        Account newAccount = accountService.login(account.getUsername(), account.getPassword());
        if (newAccount == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(newAccount);
    }

    // create a message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        if (newMessage == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(newMessage);
    }

    // get all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    // get message by id
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(null));
    }

    // delete message by id
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        boolean deleted = messageService.deleteMessage(messageId);
        return deleted ? ResponseEntity.ok(1) : ResponseEntity.ok(null);
    }

    // update message by id
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        boolean updated = messageService.updateMessage(messageId, message.getMessageText());
        return updated ? ResponseEntity.ok(1) : ResponseEntity.status(400).build();
    }

    // get message by user
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        return ResponseEntity.ok(messageService.getMessagesByUser(accountId));
    }
}

