package com.example.ebanking.bankaccount.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WebClient webClient;

    public boolean isBankAccountUsed(long id) {
        Boolean result = webClient
                .get()
                .uri("http://localhost:8762/user-service/user/isBankAccountUsed/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() != HttpStatus.OK.value(),
                        response -> Mono.error(new ServiceException("Request failed: bank-account-service isBankAccountUsed() method."))
                )
                .bodyToMono(boolean.class)
                .blockOptional()
                .orElse(null);

        if (result == null) {
            throw new IllegalStateException("Response body is null: bank-account-service isBankAccountUsed() method.");
        }

        return result;
    }

}
