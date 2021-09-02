package com.example.ebanking.insurance.service;

import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.db.repository.InsuranceRepository;
import com.example.ebanking.insurance.dto.InsuranceRequest;
import com.example.ebanking.insurance.dto.InsuranceResponse;
import com.example.ebanking.insurance.service.mapper.InsuranceMapper;
import com.example.ebanking.insurance.service.webClient.UserWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final UserWebClient userWebClient;
    private final InsuranceMapper insuranceMapper;

    public List<InsuranceResponse> findAll() {
        return insuranceRepository
                .findAll()
                .stream()
                .map(insuranceMapper::map)
                .collect(Collectors.toList());
    }

    public InsuranceResponse findById(long id) {
        Insurance insurance = insuranceRepository.findById(id).orElse(null);
        if (insurance == null) {
            throw new IllegalStateException("Insurance with id: " + id + " doesn't exists.");
        }
        return insuranceMapper.map(insurance);
    }

    public List<InsuranceResponse> findByIds(Set<Long> ids) {
        return insuranceRepository
                .findAllById(ids)
                .stream()
                .map(insuranceMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public InsuranceResponse create(InsuranceRequest insuranceRequest) {
        Insurance insurance = new Insurance();
        insurance.setType(insuranceRequest.getType());
        insurance.setNote(insuranceRequest.getNote());
        insurance.setPrice(insuranceRequest.getPrice());

        return insuranceMapper.map(
                insuranceRepository.save(insurance)
        );
    }

    @Transactional
    public void delete(long id) {
        if (userWebClient.isInsuranceUsed(id)) {
            throw new IllegalArgumentException("This insurance can not be deleted because is used by a user.");
        }
        insuranceRepository.deleteById(id);
    }

}
