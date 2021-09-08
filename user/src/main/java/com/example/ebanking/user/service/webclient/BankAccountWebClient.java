package com.example.ebanking.user.service.webclient;

import com.example.ebanking.user.dto.BankAccountResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BankAccountWebClient {

    private final WebClient webClient;

    @Value("${ebanking.zuulServer.URL}")
    private String zuulServerURL;

    @Value("${ebanking.bankAccountService.PATH}")
    private String bankAccountServicePATH;

    public BankAccountResponse getById(long id) {
        return webClient
                .get()
                .uri(zuulServerURL + bankAccountServicePATH + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() != HttpStatus.OK.value(),
                        response -> Mono.error(new ServiceException("Request failed: bank-account-service getById() method."))
                )
                .bodyToMono(BankAccountResponse.class)
                .blockOptional()
                .orElse(null);
    }

    public List<BankAccountResponse> getByIds(Set<Long> bankAccountIds) {
        return webClient
                .post()
                .uri(zuulServerURL + bankAccountServicePATH + "getByIds")
                .body(Mono.just(bankAccountIds), new ParameterizedTypeReference<Set<Long>>() {})
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() != HttpStatus.OK.value(),
                        response -> Mono.error(new ServiceException("Request failed: bank-account-service getByIds() method."))
                )
                .bodyToFlux(BankAccountResponse.class)
                .collectList()
                .blockOptional()
                .orElse(null);
    }
}
