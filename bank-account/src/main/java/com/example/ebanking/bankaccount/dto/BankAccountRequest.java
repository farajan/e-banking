package com.example.ebanking.bankaccount.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDateTime;

@Value
public class BankAccountRequest {
    long bankAccountId;
    String accountNumber;
    boolean active;
    long balance;
    @NonFinal @Setter @Getter
    LocalDateTime created;
}
