package com.example.ebanking.user.dto;

import lombok.Value;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Value
public class UserCreateRequest {

    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "Max length of first name is 50")
    String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Max length of last name is 50")
    String lastName;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 2, max = 50, message = "Length of username should be in range <2, 50>")
    String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password length should be at least 8")
    String password;

    @NotNull(message = "Email is mandatory")
    @Email
    String email;

    @NotNull(message = "First name is mandatory")
    LocalDateTime birthday;

}
