package com.example.ebanking.user.service.mapper;

public interface Mapper<E, D> {
    D map(E entity);
}
