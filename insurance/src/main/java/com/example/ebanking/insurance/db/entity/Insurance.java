package com.example.ebanking.insurance.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insuranceId;
    private String type;
    private String note;
    private int price;
}
