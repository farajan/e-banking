package com.example.ebanking.insurance.dto;

import com.example.ebanking.common.NullOrNotBlank;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class InsuranceCreateRequest {
    @NotBlank(message = "Type is mandatory")
    String type;
    @NullOrNotBlank(message = "Note should be null or not blank string")
    String note;
    int price;
}
