package com.example.ebanking.insurance.controller.mapper;

import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.dto.InsuranceResponse;
import org.springframework.stereotype.Component;

@Component
public class InsuranceMapper implements Mapper<Insurance, InsuranceResponse> {

    @Override
    public InsuranceResponse map(Insurance entity) {
        return new InsuranceResponse(
                entity.getInsuranceId(),
                entity.getType(),
                entity.getNote(),
                entity.getPrice()
        );
    }

}
