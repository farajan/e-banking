package com.example.ebanking.bankaccount.service.mapper;

public interface Mapper<E, D> {
    E mapToEntity(D dto);
    D mapFromEntity(E entity);
}
