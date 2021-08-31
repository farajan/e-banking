package com.example.ebanking.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsuranceResponse {
    private long insuranceId;
    private String type;
    private String note;
    private int price;
}
