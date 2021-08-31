package com.example.ebanking.user.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class UserResponse {
    long userId;
    String firstName;
    String lastName;
    String username;
    LocalDateTime birthday;
    List<BankAccountResponse> bankAccountList;
    List<InsuranceResponse> insuranceList;
}
