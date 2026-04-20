package com.example.demo.entity;

import com.example.demo.enums.AccountType;
import jakarta.persistence.*;

@Entity
@Table(
        name = "accounts",
        indexes = {
                @Index(name = "idx_account_iban", columnList = "iban_number")
        }
)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "iban_number", nullable = false, unique = true, length = 34)
    private String ibanNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private AccountType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
