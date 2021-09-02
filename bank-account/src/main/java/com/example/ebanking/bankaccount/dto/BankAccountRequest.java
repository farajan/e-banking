package com.example.ebanking.bankaccount.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class BankAccountRequest {
    @NotBlank
    String accountNumber;
    boolean active;
    long balance;
}
