package com.example.ebanking.bankaccount.controller;

import com.example.ebanking.bankaccount.controller.mapper.BankAccountMapper;
import com.example.ebanking.bankaccount.db.entity.BankAccount;
import com.example.ebanking.bankaccount.dto.BankAccountRequest;
import com.example.ebanking.bankaccount.dto.BankAccountResponse;
import com.example.ebanking.bankaccount.service.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BankAccountController.class)
public class BankAccountControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BankAccountService bankAccountService;

    @MockBean
    private BankAccountMapper bankAccountMapper;

    @Test
    public void getAll_shouldPass() throws Exception {
        final BankAccount bankAccount = createBankAccount();
        Page<BankAccount> bankAccountPage = new PageImpl<>(List.of(bankAccount));
        given(bankAccountService.getAll(PageRequest.of(0,100))).willReturn(bankAccountPage);

        final BankAccountResponse response = createBankAccountResponse();
        given(bankAccountMapper.map(bankAccount)).willReturn(response);

        mvc.perform(get("/bankAccount?page=0&limit=100")
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(1L))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].bankAccountId").value(response.getBankAccountId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].accountNumber").value(response.getAccountNumber()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].active").value(response.isActive()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].balance").value(response.getBalance()));
    }

    @Test
    public void getByIds_shouldPass() throws Exception {
        final BankAccount bankAccount = createBankAccount();
        given(bankAccountService.getByIds(Set.of(1L))).willReturn(List.of(bankAccount));

        final BankAccountResponse response = createBankAccountResponse();
        given(bankAccountMapper.map(bankAccount)).willReturn(response);

        mvc.perform(post("/bankAccount/getByIds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Set.of(1L))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bankAccountId").value(response.getBankAccountId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].accountNumber").value(response.getAccountNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active").value(response.isActive()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].balance").value(response.getBalance()));
    }

    @Test
    public void getById_shouldPass() throws Exception {
        final BankAccount bankAccount = createBankAccount();
        given(bankAccountService.getById(1L)).willReturn(bankAccount);

        final BankAccountResponse response = createBankAccountResponse();
        given(bankAccountMapper.map(bankAccount)).willReturn(response);

        mvc.perform(get("/bankAccount/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountId").value(response.getBankAccountId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(response.getAccountNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(response.isActive()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(response.getBalance()));
    }

    @Test
    public void create_shouldPass() throws Exception {
        final BankAccountRequest request = createBankAccountRequest();
        final BankAccount bankAccount = createBankAccount();
        final BankAccountResponse response = createBankAccountResponse();

        given(bankAccountService.create(request)).willReturn(bankAccount);
        given(bankAccountMapper.map(bankAccount)).willReturn(response);

        mvc.perform(post("/bankAccount/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(request.getAccountNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(request.isActive()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(request.getBalance()));
    }

    @Test
    public void delete_shouldPass() throws Exception {
        mvc.perform(delete("/bankAccount/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private BankAccount createBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("12345");
        bankAccount.setActive(true);
        bankAccount.setBalance(8000);
        return bankAccount;
    }

    private BankAccountResponse createBankAccountResponse() {
        return new BankAccountResponse(
                1L,
                "12345",
                true,
                8000,
                LocalDateTime.now()
        );
    }

    private BankAccountRequest createBankAccountRequest() {
        return new BankAccountRequest(
                "12345",
                true,
                8000
        );
    }

}
