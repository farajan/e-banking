package com.example.ebanking.bankaccount.service.mapper;

import com.example.ebanking.bankaccount.db.entity.BankAccount;
import com.example.ebanking.bankaccount.dto.BankAccountResponse;
import org.springframework.stereotype.Component;

@Component
public class BankAccountResponseMapper implements Mapper<BankAccount, BankAccountResponse> {

    @Override
    public BankAccount mapToEntity(BankAccountResponse bankAccountResponse) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountId(bankAccountResponse.getBankAccountId());
        bankAccount.setAccountNumber(bankAccountResponse.getAccountNumber());
        bankAccount.setActive(bankAccountResponse.isActive());
        bankAccount.setBalance(bankAccountResponse.getBalance());
        bankAccount.setCreated(bankAccountResponse.getCreated());
        return bankAccount;
    }

    @Override
    public BankAccountResponse mapFromEntity(BankAccount entity) {
        return new BankAccountResponse(
                entity.getBankAccountId(),
                entity.getAccountNumber(),
                entity.isActive(),
                entity.getBalance(),
                entity.getCreated()
        );
    }

}
