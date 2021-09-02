package com.example.ebanking.user.db.repository;

import com.example.ebanking.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUsersByBankAccountIds(long id);
    boolean existsUsersByInsuranceIds(long insuranceId);
}
