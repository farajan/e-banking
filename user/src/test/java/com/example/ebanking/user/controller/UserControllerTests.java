package com.example.ebanking.user.controller;

import com.example.ebanking.user.controller.mapper.UserMapper;
import com.example.ebanking.user.db.entity.User;
import com.example.ebanking.user.dto.BankAccountResponse;
import com.example.ebanking.user.dto.InsuranceResponse;
import com.example.ebanking.user.dto.UserRequest;
import com.example.ebanking.user.dto.UserResponse;
import com.example.ebanking.user.service.UserService;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    public void getAll_shouldPass() throws Exception {
        final User user = createUser();
        Page<User> insurancePage = new PageImpl<>(List.of(user));
        given(userService.getAll(PageRequest.of(0,100))).willReturn(insurancePage);

        final UserResponse response = createUserResponse();
        given(userMapper.map(user)).willReturn(response);

        mvc.perform(get("/user?page=0&limit=100")
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").value(response.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].username").value(response.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email").value(response.getEmail()));
    }

    @Test
    public void getById_shouldPass() throws Exception {
        final User user = createUser();
        given(userService.getById(1L)).willReturn(user);

        final UserResponse response = createUserResponse();
        given(userMapper.map(user)).willReturn(response);

        mvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(response.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(response.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(response.getEmail()));
    }

    @Test
    public void create_shouldPass() throws Exception {
        final UserRequest request = createUserRequest();
        final User user = createUser();
        final UserResponse response = createUserResponse();

        given(userService.create(request)).willReturn(user);
        given(userMapper.map(user)).willReturn(response);

        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(response.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(response.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(response.getEmail()));
    }

    @Test
    public void delete_shouldPass() throws Exception {
        mvc.perform(delete("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void addInsurance_shouldPass() throws Exception {
        final User user = createUser();
        given(userService.addInsurance(1L, 1L)).willReturn(user);

        final UserResponse response = createUserResponse();
        given(userMapper.map(user)).willReturn(response);

        final InsuranceResponse insuranceResponse = new InsuranceResponse();
        insuranceResponse.setInsuranceId(1L);
        insuranceResponse.setType("Car");
        insuranceResponse.setNote("No note");
        insuranceResponse.setPrice(900);
        response.getInsuranceList().add(insuranceResponse);

        mvc.perform(post("/user/1/insurance/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(response.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(response.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(response.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.insuranceList[0].insuranceId").value(insuranceResponse.getInsuranceId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.insuranceList[0].type").value(insuranceResponse.getType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.insuranceList[0].note").value(insuranceResponse.getNote()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.insuranceList[0].price").value(insuranceResponse.getPrice()));
    }

    @Test
    public void deleteInsurance_shouldPass() throws Exception {
        final User user = createUser();
        given(userService.deleteInsurance(1L, 1L)).willReturn(user);
        user.getInsuranceIds().add(1L);

        UserResponse response = createUserResponse();
        given(userMapper.map(user)).willReturn(response);

        mvc.perform(delete("/user/1/insurance/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(response.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(response.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(response.getEmail()));
    }

    @Test
    public void addBankAccount_shouldPass() throws Exception {
        final User user = createUser();
        given(userService.addBankAccount(1L, 1L)).willReturn(user);

        final UserResponse response = createUserResponse();
        given(userMapper.map(user)).willReturn(response);

        final BankAccountResponse bankAccountResponse = new BankAccountResponse();
        bankAccountResponse.setBankAccountId(1L);
        bankAccountResponse.setAccountNumber("123");
        bankAccountResponse.setActive(true);
        bankAccountResponse.setBalance(123);
        response.getBankAccountList().add(bankAccountResponse);

        mvc.perform(post("/user/1/bankAccount/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(response.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(response.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(response.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountList[0].bankAccountId").value(bankAccountResponse.getBankAccountId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountList[0].accountNumber").value(bankAccountResponse.getAccountNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountList[0].active").value(bankAccountResponse.isActive()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountList[0].balance").value(bankAccountResponse.getBalance()));
    }

    @Test
    public void deleteBankAccount_shouldPass() throws Exception {
        final User user = createUser();
        given(userService.deleteBankAccount(1L, 1L)).willReturn(user);
        user.getBankAccountIds().add(1L);

        UserResponse response = createUserResponse();
        given(userMapper.map(user)).willReturn(response);

        mvc.perform(delete("/user/1/bankAccount/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(response.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(response.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(response.getEmail()));
    }

    @Test
    public void isBankAccountUsed_shouldPass() throws Exception {
        given(userService.isBankAccountUsed(1L)).willReturn(false);

        mvc.perform(get("/user/isBankAccountUsed/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));
    }

    @Test
    public void isInsuranceUsed_shouldPass() throws Exception {
        given(userService.isInsuranceUsed(1L)).willReturn(false);

        mvc.perform(get("/user/isInsuranceUsed/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(false));
    }

    private User createUser() {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("Tom");
        user.setLastName("Hardy");
        user.setUsername("Tom");
        user.setPassword("drsnyTom");
        user.setEmail("tomik@gmail.com");
        user.setBirthday(LocalDateTime.MAX);
        user.setInsuranceIds(new HashSet<>());
        user.setBankAccountIds(new HashSet<>());
        return  user;
    }

    private UserResponse createUserResponse() {
        return new UserResponse(
                1L,
                "Tom",
                "Hardy",
                "drsnyTom",
                "tomik@gmail.com",
                LocalDateTime.MAX,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    private UserRequest createUserRequest() {
        return new UserRequest(
                "Tom",
                "Hardy",
                "drsnyTom",
                "passwd",
                "tomik@gmail.com",
                LocalDateTime.now()
        );
    }

    }
