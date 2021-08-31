package com.example.ebanking.user.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class UserDto {
    long userId;
    String firstName;
    String lastName;
    String username;
    String password;
    LocalDateTime birthday;
    List<BankAccountDto> bankAccountList;
    List<InsuranceDto> insuranceList;
}
