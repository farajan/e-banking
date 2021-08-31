package com.example.user.service;

import com.example.user.dto.BankAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final RestTemplate restTemplate;

    public BankAccountDto findById(long id) {
        return restTemplate.getForObject("http://localhost:8762/bank-account-service/" + id, BankAccountDto.class);
    }

}
