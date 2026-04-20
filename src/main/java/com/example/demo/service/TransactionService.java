package com.example.demo.service;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Iterable<Transaction> findAll() {
        return this.transactionRepository.findAll();
    }

    public Optional<Transaction> findById(int id) {
        return this.transactionRepository.findById(id);
    }

    public Transaction save(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    public Transaction update(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    public void delete(int id) {
        this.transactionRepository.deleteById(id);
    }
}
