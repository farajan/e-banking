package com.example.ebanking.insurance.service.mapper;

import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.dto.InsuranceResponse;
import org.springframework.stereotype.Component;

@Component
public class InsuranceResponseMapper implements Mapper<Insurance, InsuranceResponse> {

    @Override
    public Insurance mapToEntity(InsuranceResponse insuranceResponse) {
        Insurance insurance = new Insurance();
        insurance.setInsuranceId(insuranceResponse.getInsuranceId());
        insurance.setType(insuranceResponse.getType());
        insurance.setNote(insuranceResponse.getNote());
        insurance.setPrice(insuranceResponse.getPrice());
        return insurance;
    }

    @Override
    public InsuranceResponse mapFromEntity(Insurance entity) {
        return new InsuranceResponse(
                entity.getInsuranceId(),
                entity.getType(),
                entity.getNote(),
                entity.getPrice()
        );
    }

}
