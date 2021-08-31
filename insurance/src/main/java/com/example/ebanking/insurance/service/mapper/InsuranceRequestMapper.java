package com.example.ebanking.insurance.service.mapper;

import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.dto.InsuranceRequest;
import org.springframework.stereotype.Component;

@Component
public class InsuranceRequestMapper implements Mapper<Insurance, InsuranceRequest> {

    @Override
    public Insurance mapToEntity(InsuranceRequest insuranceRequest) {
        Insurance insurance = new Insurance();
        insurance.setInsuranceId(insuranceRequest.getInsuranceId());
        insurance.setType(insuranceRequest.getType());
        insurance.setNote(insuranceRequest.getNote());
        insurance.setPrice(insuranceRequest.getPrice());
        return insurance;
    }

    @Override
    public InsuranceRequest mapFromEntity(Insurance entity) {
        return new InsuranceRequest(
                entity.getInsuranceId(),
                entity.getType(),
                entity.getNote(),
                entity.getPrice()
        );
    }

}