package com.example.demo.mapper;

import com.example.demo.dto.AccountRequestDto;
import com.example.demo.dto.AccountResponseDto;
import com.example.demo.entity.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    public static AccountResponseDto toResponseDto(Account account) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(account.getId());
        accountResponseDto.setIbanNumber(account.getIbanNumber());
        accountResponseDto.setType(account.getType());
        return accountResponseDto;
    }

    public static Iterable<AccountResponseDto> toResponseDto(Iterable<Account> accounts) {
        List<AccountResponseDto> accountDtos = new ArrayList<>();
        for (Account account : accounts) {
            accountDtos.add(toResponseDto(account));
        }
        return accountDtos;
    }

    public static Account toEntity(AccountRequestDto accountRequestDto) {
        Account account = new Account();
        if (accountRequestDto.getId() != null) {
            account.setId(accountRequestDto.getId().orElseThrow());
        }
        account.setIbanNumber(accountRequestDto.getIbanNumber());
        account.setType(accountRequestDto.getType());
        return account;
    }
}
