package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.exception.*;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account insertAccount(Account acc) throws InvalidInputException, AlreadyExistsException {
        if (acc.getUsername().isEmpty() || acc.getPassword().length() < 4) {
            throw new InvalidInputException("Invalid input");
        }
        if (accountRepository.existsByUsername(acc.getUsername())) {
            throw new AlreadyExistsException("Username already exists: " + acc.getUsername());
        }
        return accountRepository.save(acc);
    }

    public Account getAccount(Account acc) {
        return accountRepository.getByUsernameAndPassword(acc.getUsername(), acc.getPassword());
    }
}
