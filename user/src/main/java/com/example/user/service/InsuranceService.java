package com.example.user.service;

import com.example.user.dto.InsuranceDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class InsuranceService {

    private final RestTemplate restTemplate;

    public InsuranceDto findById(long id) {
        return restTemplate.getForObject("http://localhost:8762/insurance-service/" + id, InsuranceDto.class);
    }

}
