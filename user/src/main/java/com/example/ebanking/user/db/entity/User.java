package com.example.ebanking.user.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private LocalDateTime birthday;
    @ElementCollection
    private Set<Long> bankAccountIds = new HashSet<>();
    @ElementCollection
    private Set<Long> insuranceIds = new HashSet<>();
}
