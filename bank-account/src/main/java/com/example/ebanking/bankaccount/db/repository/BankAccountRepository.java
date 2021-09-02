package com.example.ebanking.bankaccount.db.repository;

import com.example.ebanking.bankaccount.db.entity.BankAccount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends PagingAndSortingRepository<BankAccount, Long> {
}
