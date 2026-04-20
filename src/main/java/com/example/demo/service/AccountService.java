package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<Account> findAll() {
        return this.accountRepository.findAll();
    }

    public Optional<Account> findById(int id) {
        return this.accountRepository.findById(id);
    }

    public Account save(Account account) {
        return this.accountRepository.save(account);
    }

    public Account update(Account account) {
        return this.accountRepository.save(account);
    }

    public void delete(int id) {
        this.accountRepository.deleteById(id);
    }
}
