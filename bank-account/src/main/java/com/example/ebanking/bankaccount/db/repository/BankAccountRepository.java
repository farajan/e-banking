package com.example.ebanking.bankaccount.db.repository;

import com.example.ebanking.bankaccount.db.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
