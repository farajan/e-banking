package com.example.ebanking.bankaccount.controller.mapper;

import com.example.ebanking.bankaccount.dto.BankAccountResponse;
import com.example.ebanking.bankaccount.db.entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper implements Mapper<BankAccount, BankAccountResponse> {

    @Override
    public BankAccountResponse map(BankAccount entity) {
        return new BankAccountResponse(
                entity.getBankAccountId(),
                entity.getAccountNumber(),
                entity.isActive(),
                entity.getBalance(),
                entity.getCreated()
        );
    }

}
