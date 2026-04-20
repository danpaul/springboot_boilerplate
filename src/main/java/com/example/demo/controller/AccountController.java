package com.example.demo.controller;

import com.example.demo.dto.AccountRequestDto;
import com.example.demo.dto.AccountResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    Iterable<AccountResponseDto> getAll() {
        return AccountMapper.toResponseDto(this.accountService.findAll());
    }

    @GetMapping("/{id}")
    AccountResponseDto get(@PathVariable int id) {
        Optional<Account> account = this.accountService.findById(id);
        if (account.isEmpty()) return new AccountResponseDto();
        return AccountMapper.toResponseDto(account.get());
    }

    @PostMapping("")
    AccountResponseDto create(@RequestBody AccountRequestDto accountRequestDto) {
        Account account = this.accountService.save(AccountMapper.toEntity(accountRequestDto));
        return AccountMapper.toResponseDto(account);
    }

    @PutMapping("/{id}")
    AccountResponseDto update(@RequestBody AccountRequestDto accountRequestDto, @PathVariable int id) {
        Account account = AccountMapper.toEntity(accountRequestDto);
        account.setId(id);
        return AccountMapper.toResponseDto(this.accountService.update(account));
    }

    @DeleteMapping("/{id}")
    Object delete(@PathVariable int id) {
        this.accountService.delete(id);
        return new Object();
    }
}
