package com.example.ebanking.bankaccount.service.mapper;

import com.example.ebanking.bankaccount.dto.BankAccountRequest;
import com.example.ebanking.bankaccount.db.entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountRequestMapper implements Mapper<BankAccount, BankAccountRequest> {

    @Override
    public BankAccount mapToEntity(BankAccountRequest bankAccountRequest) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountId(bankAccountRequest.getBankAccountId());
        bankAccount.setAccountNumber(bankAccountRequest.getAccountNumber());
        bankAccount.setActive(bankAccountRequest.isActive());
        bankAccount.setBalance(bankAccountRequest.getBalance());
        bankAccount.setCreated(bankAccountRequest.getCreated());
        return bankAccount;
    }

    @Override
    public BankAccountRequest mapFromEntity(BankAccount entity) {
        return new BankAccountRequest(
                entity.getBankAccountId(),
                entity.getAccountNumber(),
                entity.isActive(),
                entity.getBalance(),
                entity.getCreated()
        );
    }

}
