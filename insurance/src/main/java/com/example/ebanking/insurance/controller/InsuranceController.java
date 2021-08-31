package com.example.ebanking.insurance.controller;

import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping("hello")
    public String hello() {
        return "Insurance service says hello world!";
    }

    @GetMapping
    public List<Insurance> getAll() {
        return insuranceService.findAll();
    }

    @GetMapping("{id}")
    public Insurance getById(@PathVariable long id) {
        return insuranceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Insurance create(@RequestBody Insurance insurance) {
        return insuranceService.create(insurance);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        insuranceService.delete(id);
    }

}
