package com.example.ebanking.bankaccount.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class BankAccountResponse {
    long bankAccountId;
    String accountNumber;
    boolean active;
    long balance;
    LocalDateTime created;
}
