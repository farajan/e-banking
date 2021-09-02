package com.example.ebanking.user.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
public class UserRequest {
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String username;
    @NotBlank
    String password;
    @NotNull @Email
    String email;
    @NotNull
    LocalDateTime birthday;


}
