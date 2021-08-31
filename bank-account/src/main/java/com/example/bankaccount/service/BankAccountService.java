package com.example.bankaccount.service;

import com.example.bankaccount.db.entity.BankAccount;
import com.example.bankaccount.db.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private final UserService userService;

    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    public BankAccount findById(long id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    public BankAccount create(BankAccount bankAccount) {
        bankAccount.setCreated(LocalDateTime.now());
        return bankAccountRepository.save(bankAccount);
    }

    public void delete(long id) {
        if (userService.isBankAccountUsed(id)) {
            throw new IllegalArgumentException("This bank account can not be deleted because is used by a user.");
        }
        bankAccountRepository.deleteById(id);
    }

}
