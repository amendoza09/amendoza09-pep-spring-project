package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;


@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<?> registerAccount(Account account) {
        if(account.getUsername() == null || account.getPassword() == null
        || account.getPassword().length() < 4) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        if(accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists.");
        }
        
        Account newAccount = accountRepository.save(account);
        return ResponseEntity.ok(newAccount);
    }

    public ResponseEntity<?> loginUser(Account account) {
        Optional<Account> existing = accountRepository.findByUsername(account.getUsername());

        if(existing.isPresent() && existing.get().getPassword().equals(account.getPassword())) {
            return ResponseEntity.ok(existing);
        }

        return ResponseEntity.status(401).body("Invalid username or password");
    }
}
