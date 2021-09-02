package com.example.ebanking.bankaccount.controller;

import com.example.ebanking.bankaccount.dto.BankAccountRequest;
import com.example.ebanking.bankaccount.dto.BankAccountResponse;
import com.example.ebanking.bankaccount.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("bankAccount")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping("hello")
    public String hello() {
        return "Bank Account service says hello world!";
    }

    @GetMapping
    public List<BankAccountResponse> getAll() {
        return bankAccountService.findAll();
    }

    @GetMapping("{id}")
    public BankAccountResponse getById(@PathVariable long id) {
        return bankAccountService.findById(id);
    }

    @PostMapping("getByIds")
    public List<BankAccountResponse> getByIds(@RequestBody Set<Long> ids) {
        return bankAccountService.findByIds(ids);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountResponse create(@RequestBody @Valid BankAccountRequest bankAccountRequest) {
        return bankAccountService.create(bankAccountRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        bankAccountService.delete(id);
    }

}
