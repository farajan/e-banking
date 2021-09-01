package com.example.ebanking.user.service;

import com.example.ebanking.user.dto.InsuranceResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final WebClient webClient;

    public InsuranceResponse findById(long id) {
        return webClient
                .get()
                .uri("http://localhost:8762/insurance-service/insurance/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() != HttpStatus.OK.value(),
                        response -> Mono.error(new ServiceException("Request failed: insurance-service getById() method."))
                )
                .bodyToMono(InsuranceResponse.class)
                .blockOptional()
                .orElse(null);
    }

    public List<InsuranceResponse> getByIds(Set<Long> insuranceIds) {
        return webClient
                .post()
                .uri("http://localhost:8762/insurance-service/insurance/getByIds")
                .body(Mono.just(insuranceIds), new ParameterizedTypeReference<Set<Long>>() {})
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() != HttpStatus.OK.value(),
                        response -> Mono.error(new ServiceException("Request failed: insurance-service getByIds() method."))
                )
                .bodyToFlux(InsuranceResponse.class)
                .collectList()
                .blockOptional()
                .orElse(null);
    }

}
