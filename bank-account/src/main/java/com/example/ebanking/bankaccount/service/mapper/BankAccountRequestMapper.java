package com.example.ebanking.bankaccount.service.mapper;

import com.example.ebanking.bankaccount.dto.BankAccountRequest;
import com.example.ebanking.bankaccount.db.entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountRequestMapper implements Mapper<BankAccount, BankAccountRequest> {

    @Override
    public BankAccount mapToEntity(BankAccountRequest bankAccountRequest) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(bankAccountRequest.getAccountNumber());
        bankAccount.setActive(bankAccountRequest.isActive());
        bankAccount.setBalance(bankAccountRequest.getBalance());
        return bankAccount;
    }

    @Override
    public BankAccountRequest mapFromEntity(BankAccount entity) {
        return new BankAccountRequest(
                entity.getAccountNumber(),
                entity.isActive(),
                entity.getBalance()
        );
    }

}
