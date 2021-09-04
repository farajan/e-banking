package com.example.ebanking.user.JUnit;


import com.example.ebanking.user.db.entity.User;
import com.example.ebanking.user.db.repository.UserRepository;
import com.example.ebanking.user.dto.BankAccountResponse;
import com.example.ebanking.user.dto.InsuranceResponse;
import com.example.ebanking.user.dto.UserRequest;
import com.example.ebanking.user.service.UserService;
import com.example.ebanking.user.service.webclient.BankWebClient;
import com.example.ebanking.user.service.webclient.InsuranceWebClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BankWebClient bankWebClient;

    @Mock
    private InsuranceWebClient insuranceWebClient;

    @Test
    public void getAll_shouldPass() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        Page<User> userPage = new PageImpl<>(Arrays.asList(user1, user2, user3));
        PageRequest pageRequest = PageRequest.of(0, 3);

        when(userRepository.findAll(pageRequest)).thenReturn(userPage);

        userService.findAll(pageRequest);

        verify(userRepository, times(1)).findAll(pageRequest);

        assertThat("Size is not equal to 3", userService.findAll(pageRequest).getTotalElements(), is(3L));
    }

    @Test
    public void getById_shouldPass() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.findById(1L);

        verify(userRepository, times(1)).findById(1L);

        assertThat("User ID is not 1L", userService.findById(1L).getUserId(), is(1L));
    }

    @Test
    public void create_shouldPass() {
        LocalDateTime now = LocalDateTime.now();

        UserRequest insuranceRequest = new UserRequest(
                "firstName",
                "lastName",
                "username",
                "password",
                "email@gmail.com",
                now
        );

        userService.create(insuranceRequest);
        User user = new User();
        user.setUsername("firstName");
        user.setLastName("lastName");
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        user.setBirthday(now);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void delete_shouldPass() {
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void addInsurance_shouldPass() {
        User user = new User();
        InsuranceResponse insuranceResponse = new InsuranceResponse();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(insuranceWebClient.findById(1L)).thenReturn(insuranceResponse);

        userService.addInsurance(1L, 1L);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void deleteInsurance_shouldPass() {
        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteInsurance(1L, 1L);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void addBankAccount_shouldPass() {
        User user = new User();
        BankAccountResponse bankAccountResponse = new BankAccountResponse();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bankWebClient.findById(1L)).thenReturn(bankAccountResponse);

        userService.addBankAccount(1L, 1L);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void deleteBankAccount_shouldPass() {
        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteBankAccount(1L, 1L);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void isBankAccountUsed_shouldPass() {
        userService.isBankAccountUsed(1L);
        verify(userRepository, times(1)).existsUsersByBankAccountIds(1L);
    }

    @Test
    public void isInsuranceUsed_shouldPass() {
        userService.isInsuranceUsed(1L);
        verify(userRepository, times(1)).existsUsersByInsuranceIds(1L);
    }

}
