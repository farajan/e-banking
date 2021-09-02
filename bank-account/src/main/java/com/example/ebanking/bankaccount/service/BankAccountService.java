package com.example.ebanking.bankaccount.service;

import com.example.ebanking.bankaccount.dto.BankAccountRequest;
import com.example.ebanking.bankaccount.dto.BankAccountResponse;
import com.example.ebanking.bankaccount.service.mapper.BankAccountRequestMapper;
import com.example.ebanking.bankaccount.service.mapper.BankAccountResponseMapper;
import com.example.ebanking.bankaccount.db.entity.BankAccount;
import com.example.ebanking.bankaccount.db.repository.BankAccountRepository;
import com.example.ebanking.bankaccount.service.webClient.UserWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountRequestMapper bankAccountRequestMapper;
    private final BankAccountResponseMapper bankAccountResponseMapper;

    private final UserWebClient userWebClient;

    public List<BankAccountResponse> findAll() {
        return bankAccountRepository
                .findAll()
                .stream()
                .map(bankAccountResponseMapper::mapFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public BankAccountResponse findById(long id) {
        final BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);
        if (bankAccount == null) {
            throw new IllegalStateException("Bank account with id: " + id + " doesn't exists.");
        }
        return bankAccountResponseMapper.mapFromEntity(bankAccount);
    }


    public List<BankAccountResponse> findByIds(Set<Long> ids) {
        return bankAccountRepository
                .findAllById(ids)
                .stream()
                .map(bankAccountResponseMapper::mapFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public BankAccountResponse create(BankAccountRequest bankAccountRequest) {
        final BankAccount bankAccount = bankAccountRequestMapper.mapToEntity(bankAccountRequest);
        bankAccount.setCreated(LocalDateTime.now());
        return bankAccountResponseMapper.mapFromEntity(
                bankAccountRepository.save(bankAccount)
        );
    }

    @Transactional
    public void delete(long id) {
        if (userWebClient.isBankAccountUsed(id)) {
            throw new IllegalArgumentException("This bank account can not be deleted because is used by a user.");
        }
        bankAccountRepository.deleteById(id);
    }

}
