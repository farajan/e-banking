package com.example.ebanking.insurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;

    public boolean isInsuranceUsed(long id) {
        return restTemplate.getForObject("http://localhost:8762/user-service/isInsuranceUsed/" + id, boolean.class);
    }

}
