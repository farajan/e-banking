package com.example.ebanking.insurance.service;

import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.db.repository.InsuranceRepository;
import com.example.ebanking.insurance.dto.InsuranceCreateRequest;
import com.example.ebanking.insurance.service.proxy.UserServiceProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final UserServiceProxy userServiceProxy;

    public Page<Insurance> getAll(Pageable pageable) {
        return insuranceRepository.findAll(pageable);
    }

    public Insurance getById(long id) {
        Insurance insurance = insuranceRepository.findById(id).orElse(null);
        if (insurance == null) {
            throw new IllegalStateException("Insurance with id: " + id + " doesn't exists.");
        }
        return insurance;
    }

    public Iterable<Insurance> getByIds(Set<Long> ids) {
        return insuranceRepository.findAllById(ids);
    }

    @Transactional
    public Insurance create(@Valid InsuranceCreateRequest insuranceCreateRequest) {
        Insurance insurance = new Insurance();
        insurance.setType(insuranceCreateRequest.getType());
        insurance.setNote(insuranceCreateRequest.getNote());
        insurance.setPrice(insuranceCreateRequest.getPrice());

        return insuranceRepository.save(insurance);
    }

    @Transactional
    public void delete(long id) {
        if (userServiceProxy.isInsuranceUsed(id)) {
            throw new IllegalArgumentException("This insurance can not be deleted because is used by a user.");
        }
        insuranceRepository.deleteById(id);
    }

}
