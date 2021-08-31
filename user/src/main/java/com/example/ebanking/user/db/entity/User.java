package com.example.ebanking.user.db.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private LocalDateTime birthday;

    @ElementCollection
    private Set<Long> bankAccountIds = new HashSet<>();

    @ElementCollection
    private Set<Long> insuranceIds = new HashSet<>();

}
