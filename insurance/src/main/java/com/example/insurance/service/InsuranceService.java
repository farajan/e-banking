package com.example.insurance.service;

import com.example.insurance.db.entity.Insurance;
import com.example.insurance.db.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    private final UserService userService;

    public List<Insurance> findAll() {
        return insuranceRepository.findAll();
    }

    public Insurance findById(long id) {
        return insuranceRepository.findById(id).orElse(null);
    }

    public Insurance create(Insurance insurance) {
        return insuranceRepository.save(insurance);
    }

    public void delete(long id) {
        if (userService.isInsuranceUsed(id)) {
            throw new IllegalArgumentException("This insurance can not be deleted because is used by a user.");
        }
        insuranceRepository.deleteById(id);
    }

}
