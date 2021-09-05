package com.example.ebanking.insurance.controller;

import com.example.ebanking.insurance.controller.mapper.InsuranceMapper;
import com.example.ebanking.insurance.service.InsuranceService;
import com.example.ebanking.insurance.dto.InsuranceRequest;
import com.example.ebanking.insurance.dto.InsuranceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;
    private final InsuranceMapper insuranceMapper;

    @GetMapping
    public Page<InsuranceResponse> getAll(@RequestParam int page, @RequestParam int limit) {
        List<InsuranceResponse> insuranceResponseList = insuranceService
                .getAll(PageRequest.of(page, limit))
                .stream()
                .map(insuranceMapper::map)
                .collect(Collectors.toList());

        return new PageImpl<>(insuranceResponseList);
    }

    @GetMapping("{id}")
    public InsuranceResponse getById(@PathVariable long id) {
        return insuranceMapper.map(
                insuranceService.getById(id)
        );
    }

    @PostMapping("getByIds")
    public List<InsuranceResponse> getByIds(@RequestBody Set<Long> ids) {
        return StreamSupport
                .stream(insuranceService.getByIds(ids).spliterator(), false)
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
