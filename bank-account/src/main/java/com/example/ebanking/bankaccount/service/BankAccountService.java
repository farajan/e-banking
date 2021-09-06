package com.example.ebanking.bankaccount.service;

import com.example.ebanking.bankaccount.dto.BankAccountCreateRequest;
import com.example.ebanking.bankaccount.db.entity.BankAccount;
import com.example.ebanking.bankaccount.db.repository.BankAccountRepository;
import com.example.ebanking.bankaccount.service.proxy.UserServiceProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@Validated
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserServiceProxy userServiceProxy;

    public Page<BankAccount> getAll(Pageable pageable) {
        return bankAccountRepository.findAll(pageable);
    }

    @Transactional
    public BankAccount getById(long id) {
        final BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);
        if (bankAccount == null) {
            throw new IllegalStateException("Bank account with id: " + id + " doesn't exists.");
        }
        return bankAccount;
    }


    public Iterable<BankAccount> getByIds(Set<Long> ids) {
        return bankAccountRepository.findAllById(ids);
    }

    @Transactional
    public BankAccount create(@Valid BankAccountCreateRequest bankAccountCreateRequest) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(bankAccountCreateRequest.getAccountNumber());
        bankAccount.setActive(bankAccountCreateRequest.isActive());
        bankAccount.setBalance(bankAccountCreateRequest.getBalance());
        bankAccount.setCreated(LocalDateTime.now());

        return bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public void delete(long id) {
        if (userServiceProxy.isBankAccountUsed(id)) {
            throw new IllegalArgumentException("This bank account can not be deleted because is used by a user.");
        }
        bankAccountRepository.deleteById(id);
    }

}
