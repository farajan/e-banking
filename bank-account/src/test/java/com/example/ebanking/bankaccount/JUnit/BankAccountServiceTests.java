package com.example.ebanking.bankaccount.JUnit;


import com.example.ebanking.bankaccount.db.entity.BankAccount;
import com.example.ebanking.bankaccount.db.repository.BankAccountRepository;
import com.example.ebanking.bankaccount.dto.BankAccountRequest;
import com.example.ebanking.bankaccount.service.BankAccountService;
import com.example.ebanking.bankaccount.service.proxy.UserServiceProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountServiceTests {

    @InjectMocks
    private BankAccountService bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private UserServiceProxy userServiceProxy;


    @Test
    public void getAll_shouldPass() {
        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        BankAccount bankAccount3 = new BankAccount();
        Page<BankAccount> bankAccountPage = new PageImpl<>(Arrays.asList(bankAccount1, bankAccount2, bankAccount3));
        PageRequest pageRequest = PageRequest.of(0, 3);

        when(bankAccountRepository.findAll(pageRequest)).thenReturn(bankAccountPage);

        bankAccountService.findAll(pageRequest);

        verify(bankAccountRepository, times(1)).findAll(pageRequest);

        assertThat("Size is not equal to 3", bankAccountService.findAll(pageRequest).getTotalElements(), is(3L));
    }

    @Test
    public void getById_shouldPass() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountId(1L);
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));

        bankAccountService.findById(1L);

        verify(bankAccountRepository, times(1)).findById(1L);

        assertThat("Bank account ID is not 1L", bankAccountService.findById(1L).getBankAccountId(), is(1L));
    }

    @Test
    public void create_shouldPass() {
        BankAccountRequest bankAccount = new BankAccountRequest(
                "123456",
                true,
                124
        );

        bankAccountService.create(bankAccount);

//        verify(bankAccountRepository, times(1)).save(bankAccount);
    }

    @Test
    public void delete_shouldPass() {
        bankAccountService.delete(1L);
        verify(bankAccountRepository, times(1)).deleteById(1L);
    }



}
