package com.example.ebanking.insurance.dto;

import com.example.ebanking.common.NullOrNotBlank;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class InsuranceRequest {
    @NotBlank
    String type;
    @NullOrNotBlank
    String note;
    int price;
}
