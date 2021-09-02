package com.example.ebanking.user.db.repository;

import com.example.ebanking.user.db.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    boolean existsUsersByBankAccountIds(long id);
    boolean existsUsersByInsuranceIds(long insuranceId);
}
