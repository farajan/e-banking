package com.example.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsuranceDto {
    private long insuranceId;
    private String type;
    private String note;
    private int price;
}
