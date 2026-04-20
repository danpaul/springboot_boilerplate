package com.example.demo.mapper;

import com.example.demo.dto.TransactionRequestDto;
import com.example.demo.dto.TransactionResponseDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionMapper {

    public static TransactionResponseDto toResponseDto(Transaction transaction) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setId(transaction.getId());
        transactionResponseDto.setFromAccountId(transaction.getFromAccount().getId());
        transactionResponseDto.setToAccountId(transaction.getToAccount().getId());
        transactionResponseDto.setAmount(transaction.getAmount());
        return transactionResponseDto;
    }

    public static Iterable<TransactionResponseDto> toResponseDto(Iterable<Transaction> transactions) {
        List<TransactionResponseDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDtos.add(toResponseDto(transaction));
        }
        return transactionDtos;
    }

    public static Transaction toEntity(TransactionRequestDto transactionRequestDto) {
        Transaction transaction = new Transaction();
        if (transactionRequestDto.getId() != null) {
            transaction.setId(transactionRequestDto.getId().orElseThrow());
        }
        transaction.setFromAccount(toAccountReference(transactionRequestDto.getFromAccountId()));
        transaction.setToAccount(toAccountReference(transactionRequestDto.getToAccountId()));
        transaction.setAmount(transactionRequestDto.getAmount());
        return transaction;
    }

    private static Account toAccountReference(int id) {
        Account account = new Account();
        account.setId(id);
        return account;
    }
}
