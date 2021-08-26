package com.example.user.controller;

import com.example.user.db.entity.User;
import com.example.user.dto.UserDto;
import com.example.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("hello")
    public String hello() {
        return "User service says hello world!";
    }

    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public UserDto getById(@PathVariable long id) {
        return userService.getById(id);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }

    @PostMapping("{userId}/addBankAccount/{bankAccountId}")
    public UserDto addBankAccount(@PathVariable long userId, @PathVariable long bankAccountId) {
        return userService.addBankAccount(userId, bankAccountId);
    }

    @PostMapping("{userId}/deleteBankAccount/{bankAccountId}")
    public UserDto deleteBankAccount(@PathVariable long userId, @PathVariable long bankAccountId) {
        return userService.deleteBankAccount(userId, bankAccountId);
    }

    @PostMapping("{userId}/addInsurance/{insuranceId}")
    public UserDto addInsurance(@PathVariable long userId, @PathVariable long insuranceId) {
        return userService.addInsurance(userId, insuranceId);
    }

    @PostMapping("{userId}/deleteInsurance/{insuranceId}")
    public UserDto deleteInsurance(@PathVariable long userId, @PathVariable long insuranceId) {
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
