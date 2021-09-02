package com.example.ebanking.insurance.controller;

import com.example.ebanking.insurance.controller.mapper.InsuranceMapper;
import com.example.ebanking.insurance.service.InsuranceService;
import com.example.ebanking.insurance.dto.InsuranceRequest;
import com.example.ebanking.insurance.dto.InsuranceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;
    private final InsuranceMapper insuranceMapper;

    @GetMapping
    public List<InsuranceResponse> getAll() {
        return insuranceService
                .findAll()
                .stream()
                .map(insuranceMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public InsuranceResponse getById(@PathVariable long id) {
        return insuranceMapper.map(
                insuranceService.findById(id)
        );
    }

    @PostMapping("getByIds")
    public List<InsuranceResponse> getByIds(@RequestBody Set<Long> ids) {
        return insuranceService
                .findByIds(ids)
                .stream()
                .map(insuranceMapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InsuranceResponse create(@RequestBody @Valid InsuranceRequest insuranceRequest) {
        return insuranceMapper.map(
                insuranceService.create(insuranceRequest)
        );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        insuranceService.delete(id);
    }

}
