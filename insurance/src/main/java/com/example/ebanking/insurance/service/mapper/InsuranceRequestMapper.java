package com.example.ebanking.insurance.service.mapper;

import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.dto.InsuranceRequest;
import org.springframework.stereotype.Component;

@Component
public class InsuranceRequestMapper implements Mapper<Insurance, InsuranceRequest> {

    @Override
    public Insurance mapToEntity(InsuranceRequest insuranceRequest) {
        Insurance insurance = new Insurance();
        insurance.setType(insuranceRequest.getType());
        insurance.setNote(insuranceRequest.getNote());
        insurance.setPrice(insuranceRequest.getPrice());
        return insurance;
    }

    @Override
    public InsuranceRequest mapFromEntity(Insurance entity) {
        return new InsuranceRequest(
                entity.getType(),
                entity.getNote(),
                entity.getPrice()
        );
    }

}