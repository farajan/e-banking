package com.example.ebanking.insurance.controller;

import com.example.ebanking.insurance.service.InsuranceService;
import com.example.ebanking.insurance.dto.InsuranceRequest;
import com.example.ebanking.insurance.dto.InsuranceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping("hello")
    public String hello() {
        return "Insurance service says hello world!";
    }

    @GetMapping
    public List<InsuranceResponse> getAll() {
        return insuranceService.findAll();
    }

    @GetMapping("{id}")
    public InsuranceResponse getById(@PathVariable long id) {
        return insuranceService.findById(id);
    }

    @PostMapping("getByIds")
    public List<InsuranceResponse> getByIds(@RequestBody Set<Long> ids) {
        return insuranceService.findByIds(ids);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InsuranceResponse create(@RequestBody InsuranceRequest insuranceRequest) {
        return insuranceService.create(insuranceRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        insuranceService.delete(id);
    }

}
