package com.example.ebanking.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BankAccountDto {
    private long bankAccountId;
    private String accountNumber;
    private boolean active;
    private long balance;
    private LocalDateTime created;
}
