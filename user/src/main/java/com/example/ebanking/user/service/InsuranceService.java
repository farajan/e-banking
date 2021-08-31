package com.example.ebanking.user.service;

import com.example.ebanking.user.dto.InsuranceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final RestTemplate restTemplate;

    public InsuranceDto findById(long id) {
        return restTemplate.getForObject("http://localhost:8762/insurance-service/" + id, InsuranceDto.class);
    }

}
