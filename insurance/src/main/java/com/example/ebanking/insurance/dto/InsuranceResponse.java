package com.example.ebanking.insurance.dto;

import lombok.Value;

@Value
public class InsuranceResponse {
    long insuranceId;
    String type;
    String note;
    int price;
}
