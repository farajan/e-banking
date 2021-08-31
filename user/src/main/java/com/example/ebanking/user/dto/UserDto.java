package com.example.ebanking.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long userId;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDateTime birthday;
    private List<BankAccountDto> bankAccountList;
    private List<InsuranceDto> insuranceList;
}
