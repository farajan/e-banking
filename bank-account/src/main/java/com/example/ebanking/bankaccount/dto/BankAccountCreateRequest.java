package com.example.ebanking.bankaccount.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class BankAccountCreateRequest {
    @NotBlank(message = "Account number is mandatory")
    @Size(min = 2, max = 50, message = "Account number length should be in range <2, 50>")
    String accountNumber;
    boolean active;
    long balance;
}
