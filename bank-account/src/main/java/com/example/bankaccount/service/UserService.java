package com.example.bankaccount.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;

    public boolean isBankAccountUsed(long id) {
        return restTemplate.getForObject("http://localhost:8762/user-service/isBankAccountUsed/" + id, boolean.class);
    }

}
