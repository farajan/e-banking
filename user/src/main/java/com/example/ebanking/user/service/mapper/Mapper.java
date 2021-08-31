package com.example.ebanking.user.service.mapper;

public interface Mapper<E, D> {
    E mapToEntity(D dto);
    D mapFromEntity(E entity);
}
