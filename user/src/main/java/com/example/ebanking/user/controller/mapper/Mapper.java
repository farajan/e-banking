package com.example.ebanking.user.controller.mapper;

public interface Mapper<E, D> {
    D map(E entity);
}
