package com.example.ebanking.bankaccount.service;

import com.example.ebanking.bankaccount.dto.BankAccountRequest;
import com.example.ebanking.bankaccount.dto.BankAccountResponse;
import com.example.ebanking.bankaccount.service.mapper.BankAccountMapper;
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
    private final BankAccountMapper bankAccountMapper;
    private final UserWebClient userWebClient;

    public List<BankAccountResponse> findAll() {
        return bankAccountRepository
                .findAll()
                .stream()
                .map(bankAccountMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public BankAccountResponse findById(long id) {
        final BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);
        if (bankAccount == null) {
            throw new IllegalStateException("Bank account with id: " + id + " doesn't exists.");
        }
        return bankAccountMapper.map(bankAccount);
    }


    public List<BankAccountResponse> findByIds(Set<Long> ids) {
        return bankAccountRepository
                .findAllById(ids)
                .stream()
                .map(bankAccountMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public BankAccountResponse create(BankAccountRequest bankAccountRequest) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(bankAccountRequest.getAccountNumber());
        bankAccount.setActive(bankAccountRequest.isActive());
        bankAccount.setBalance(bankAccountRequest.getBalance());
        bankAccount.setCreated(LocalDateTime.now());

        return bankAccountMapper.map(
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
