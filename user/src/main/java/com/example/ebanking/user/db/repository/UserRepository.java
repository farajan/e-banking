package com.example.ebanking.user.db.repository;

import com.example.ebanking.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getUsersByBankAccountIds(long id);
    List<User> getUsersByInsuranceIds(long insuranceId);
}
