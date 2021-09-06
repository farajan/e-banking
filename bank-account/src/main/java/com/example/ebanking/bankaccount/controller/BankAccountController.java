package com.example.ebanking.bankaccount.controller;

import com.example.ebanking.bankaccount.controller.mapper.BankAccountMapper;
import com.example.ebanking.bankaccount.dto.BankAccountCreateRequest;
import com.example.ebanking.bankaccount.dto.BankAccountResponse;
import com.example.ebanking.bankaccount.service.BankAccountService;
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
@RequestMapping("bankAccount")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @GetMapping
    public Page<BankAccountResponse> getAll(@RequestParam int page, @RequestParam int limit) {
        List<BankAccountResponse> bankAccountResponseList = bankAccountService
                .getAll(PageRequest.of(page,limit))
                .stream()
                .map(bankAccountMapper::map)
                .collect(Collectors.toList());

        return new PageImpl<>(bankAccountResponseList);
    }

    @GetMapping("{id}")
    public BankAccountResponse getById(@PathVariable long id) {
        return bankAccountMapper.map(
                bankAccountService.getById(id)
        );
    }

    @PostMapping("getByIds")
    public List<BankAccountResponse> getByIds(@RequestBody Set<Long> ids) {
        return StreamSupport
                .stream(bankAccountService.getByIds(ids).spliterator(), false)
                .map(bankAccountMapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountResponse create(@RequestBody @Valid BankAccountCreateRequest bankAccountCreateRequest) {
        return bankAccountMapper.map(
                bankAccountService.create(bankAccountCreateRequest)
        );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        bankAccountService.delete(id);
    }

}
