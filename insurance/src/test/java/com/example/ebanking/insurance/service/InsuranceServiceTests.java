package com.example.ebanking.insurance.service;


import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.db.repository.InsuranceRepository;
import com.example.ebanking.insurance.dto.InsuranceRequest;
import com.example.ebanking.insurance.service.proxy.UserServiceProxy;
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
public class InsuranceServiceTests {

    @InjectMocks
    private InsuranceService insuranceService;

    @Mock
    private InsuranceRepository insuranceRepository;

    @Mock
    private UserServiceProxy userServiceProxy;

    @Test
    public void getAll_shouldPass() {
        Insurance insurance1 = new Insurance();
        Insurance insurance2 = new Insurance();
        Insurance insurance3 = new Insurance();
        Page<Insurance> insurancePage = new PageImpl<>(Arrays.asList(insurance1, insurance2, insurance3));
        PageRequest pageRequest = PageRequest.of(0, 3);

        when(insuranceRepository.findAll(pageRequest)).thenReturn(insurancePage);

        insuranceService.getAll(pageRequest);

        verify(insuranceRepository, times(1)).findAll(pageRequest);

        assertThat("Size is not equal to 3", insuranceService.getAll(pageRequest).getTotalElements(), is(3L));
    }

    @Test
    public void getById_shouldPass() {
        Insurance insurance = new Insurance();
        insurance.setInsuranceId(1L);
        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(insurance));

        insuranceService.getById(1L);

        verify(insuranceRepository, times(1)).findById(1L);

        assertThat("Insurance ID is not 1L", insuranceService.getById(1L).getInsuranceId(), is(1L));
    }

    @Test
    public void create_shouldPass() {
        InsuranceRequest insuranceRequest = new InsuranceRequest(
                "Car",
                "Under 1 million czk",
                9000
        );

        insuranceService.create(insuranceRequest);

        Insurance insurance = new Insurance();
        insurance.setType("Car");
        insurance.setNote("Under 1 million czk");
        insurance.setPrice(9000);

        verify(insuranceRepository, times(1)).save(insurance);
    }

    @Test
    public void delete_shouldPass() {
        insuranceService.delete(1L);
        verify(insuranceRepository, times(1)).deleteById(1L);
    }



}
