package com.example.ebanking.user.controller;

import com.example.ebanking.user.dto.UserRequest;
import com.example.ebanking.user.dto.UserResponse;
import com.example.ebanking.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("hello")
    public String hello() {
        return "User service says hello world!";
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public UserResponse getById(@PathVariable long id) {
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserRequest userRequest) {
        return userService.create(userRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }

    @PostMapping("{userId}/addBankAccount/{bankAccountId}")
    public UserResponse addBankAccount(@PathVariable long userId, @PathVariable long bankAccountId) {
        return userService.addBankAccount(userId, bankAccountId);
    }

    @PostMapping("{userId}/deleteBankAccount/{bankAccountId}")
    public UserResponse deleteBankAccount(@PathVariable long userId, @PathVariable long bankAccountId) {
        return userService.deleteBankAccount(userId, bankAccountId);
    }

    @PostMapping("{userId}/addInsurance/{insuranceId}")
    public UserResponse addInsurance(@PathVariable long userId, @PathVariable long insuranceId) {
        return userService.addInsurance(userId, insuranceId);
    }

    @PostMapping("{userId}/deleteInsurance/{insuranceId}")
    public UserResponse deleteInsurance(@PathVariable long userId, @PathVariable long insuranceId) {
        return userService.deleteInsurance(userId, insuranceId);
    }

    @GetMapping("isBankAccountUsed/{bankAccountId}")
    public boolean isBankAccountUsed(@PathVariable long bankAccountId) {
        return userService.isBankAccountUsed(bankAccountId);
    }

    @GetMapping("isInsuranceUsed/{insuranceId}")
    public boolean isInsuranceUsed(@PathVariable long insuranceId) {
        return userService.isInsuranceUsed(insuranceId);
    }

}
