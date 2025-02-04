package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidRequestException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() || 
            account.getPassword() == null || account.getPassword().length() < 4) {
            throw new InvalidRequestException("Invalid username or password");
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new DuplicateUsernameException("Username already exists");
        }
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            return account.get();
        }

        throw new UnauthorizedException("Invalid username or password");
    }
}
