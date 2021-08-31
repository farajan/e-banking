package com.example.ebanking.user.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class UserRequest {
    long userId;
    String firstName;
    String lastName;
    String username;
    String password;
    LocalDateTime birthday;
}
