package com.example.ebanking.insurance.service;

import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.db.repository.InsuranceRepository;
import com.example.ebanking.insurance.dto.InsuranceRequest;
import com.example.ebanking.insurance.service.webclient.UserWebClient;
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
    private final UserWebClient userWebClient;

    public Page<Insurance> findAll(Pageable pageable) {
        return insuranceRepository.findAll(pageable);
    }

    public Insurance findById(long id) {
        Insurance insurance = insuranceRepository.findById(id).orElse(null);
        if (insurance == null) {
            throw new IllegalStateException("Insurance with id: " + id + " doesn't exists.");
        }
        return insurance;
    }

    public Iterable<Insurance> findByIds(Set<Long> ids) {
        return insuranceRepository.findAllById(ids);
    }

    @Transactional
    public Insurance create(@Valid InsuranceRequest insuranceRequest) {
        Insurance insurance = new Insurance();
        insurance.setType(insuranceRequest.getType());
        insurance.setNote(insuranceRequest.getNote());
        insurance.setPrice(insuranceRequest.getPrice());

        return insuranceRepository.save(insurance);
    }

    @Transactional
    public void delete(long id) {
        if (userWebClient.isInsuranceUsed(id)) {
            throw new IllegalArgumentException("This insurance can not be deleted because is used by a user.");
        }
        insuranceRepository.deleteById(id);
    }

}
