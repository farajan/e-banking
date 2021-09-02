package com.example.ebanking.bankaccount.controller;

import com.example.ebanking.bankaccount.controller.mapper.BankAccountMapper;
import com.example.ebanking.bankaccount.dto.BankAccountRequest;
import com.example.ebanking.bankaccount.dto.BankAccountResponse;
import com.example.ebanking.bankaccount.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("bankAccount")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @GetMapping
    public List<BankAccountResponse> getAll() {
        return bankAccountService
                .findAll()
                .stream()
                .map(bankAccountMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public BankAccountResponse getById(@PathVariable long id) {
        return bankAccountMapper.map(
                bankAccountService.findById(id)
        );
    }

    @PostMapping("getByIds")
    public List<BankAccountResponse> getByIds(@RequestBody Set<Long> ids) {
        return bankAccountService
                .findByIds(ids)
                .stream()
                .map(bankAccountMapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountResponse create(@RequestBody @Valid BankAccountRequest bankAccountRequest) {
        return bankAccountMapper.map(
                bankAccountService.create(bankAccountRequest)
        );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        bankAccountService.delete(id);
    }

}
