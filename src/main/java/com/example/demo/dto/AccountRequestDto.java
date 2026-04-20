package com.example.demo.dto;

import com.example.demo.enums.AccountType;

import java.util.Optional;

public class AccountRequestDto {
    private Optional<Integer> id;
    private String ibanNumber;
    private AccountType type;

    public Optional<Integer> getId() {
        return id;
    }

    public void setId(Optional<Integer> id) {
        this.id = id;
    }

    public String getIbanNumber() {
        return ibanNumber;
    }

    public void setIbanNumber(String ibanNumber) {
        this.ibanNumber = ibanNumber;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
