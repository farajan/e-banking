package com.example.ebanking.bankaccount.controller;

import com.example.ebanking.bankaccount.db.entity.BankAccount;
import com.example.ebanking.bankaccount.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping("hello")
    public String hello() {
        return "Bank Account service says hello world!";
    }

    @GetMapping
    public List<BankAccount> getAll() {
        return bankAccountService.findAll();
    }

    @GetMapping("{id}")
    public BankAccount getById(@PathVariable long id) {
        return bankAccountService.findById(id);
    }

    @PostMapping
    public BankAccount create(@RequestBody BankAccount bankAccount) {
        return bankAccountService.create(bankAccount);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        bankAccountService.delete(id);
    }

}
