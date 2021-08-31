package com.example.ebanking.bankaccount.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bankAccountId;

    @NotNull
    private String accountNumber;

    @NotNull
    private boolean active;

    @NotNull
    private long balance;

    private LocalDateTime created;

}
