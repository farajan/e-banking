package com.example.ebanking.insurance.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final WebClient webClient;

    public boolean isInsuranceUsed(long id) {
        Boolean result = webClient
                .get()
                .uri("http://localhost:8762/user-service/user/isInsuranceUsed/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() != HttpStatus.OK.value(),
                        response -> Mono.error(new ServiceException("Request failed: insurance-service isInsuranceUsed() method."))
                )
                .bodyToMono(boolean.class)
                .blockOptional()
                .orElse(null);

        if (result == null) {
            throw new IllegalStateException("Response body is null: insurance-service isInsuranceUsed() method.");
        }

        return result;
    }

}
