package com.example.ebanking.insurance.db.repository;

import com.example.ebanking.insurance.db.entity.Insurance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends PagingAndSortingRepository<Insurance, Long> {
}
