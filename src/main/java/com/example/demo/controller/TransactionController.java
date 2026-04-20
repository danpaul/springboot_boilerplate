package com.example.demo.controller;

import com.example.demo.dto.TransactionRequestDto;
import com.example.demo.dto.TransactionResponseDto;
import com.example.demo.entity.Transaction;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    Iterable<TransactionResponseDto> getAll() {
        return TransactionMapper.toResponseDto(this.transactionService.findAll());
    }

    @GetMapping("/{id}")
    TransactionResponseDto get(@PathVariable int id) {
        Optional<Transaction> transaction = this.transactionService.findById(id);
        if (transaction.isEmpty()) return new TransactionResponseDto();
        return TransactionMapper.toResponseDto(transaction.get());
    }

    @PostMapping("")
    TransactionResponseDto create(@RequestBody TransactionRequestDto transactionRequestDto) {
        Transaction transaction = this.transactionService.save(TransactionMapper.toEntity(transactionRequestDto));
        return TransactionMapper.toResponseDto(transaction);
    }

    @PutMapping("/{id}")
    TransactionResponseDto update(@RequestBody TransactionRequestDto transactionRequestDto, @PathVariable int id) {
        Transaction transaction = TransactionMapper.toEntity(transactionRequestDto);
        transaction.setId(id);
        return TransactionMapper.toResponseDto(this.transactionService.update(transaction));
    }

    @DeleteMapping("/{id}")
    Object delete(@PathVariable int id) {
        this.transactionService.delete(id);
        return new Object();
    }
}
