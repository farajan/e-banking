package com.example.ebanking.user.controller;

import com.example.ebanking.user.dto.UserRequest;
import com.example.ebanking.user.dto.UserResponse;
import com.example.ebanking.user.service.UserService;
import com.example.ebanking.user.controller.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Page<UserResponse> getAll(@RequestParam int page, @RequestParam int limit) {
        List<UserResponse> userResponseList = userService
                .getAll(PageRequest.of(page, limit))
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList());

        return new PageImpl<>(userResponseList);
    }

    @GetMapping("{id}")
    public UserResponse getById(@PathVariable long id) {
        return userMapper.map(
                userService.getById(id)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserRequest userRequest) {
        return userMapper.map(
                userService.create(userRequest)
        );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }

    @PostMapping("{userId}/bankAccount/{bankAccountId}")
    public UserResponse addBankAccount(@PathVariable long userId, @PathVariable long bankAccountId) {
        return userMapper.map(
                userService.addBankAccount(userId, bankAccountId)
        );
    }

    @DeleteMapping("{userId}/bankAccount/{bankAccountId}")
    public UserResponse deleteBankAccount(@PathVariable long userId, @PathVariable long bankAccountId) {
        return userMapper.map(
                userService.deleteBankAccount(userId, bankAccountId)
        );
    }

    @PostMapping("{userId}/insurance/{insuranceId}")
    public UserResponse addInsurance(@PathVariable long userId, @PathVariable long insuranceId) {
        return userMapper.map(
                userService.addInsurance(userId, insuranceId)
        );
    }

    @DeleteMapping("{userId}/insurance/{insuranceId}")
    public UserResponse deleteInsurance(@PathVariable long userId, @PathVariable long insuranceId) {
        return userMapper.map(
                userService.deleteInsurance(userId, insuranceId)
        );
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
