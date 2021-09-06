package com.example.ebanking.insurance.controller;

import com.example.ebanking.insurance.controller.mapper.InsuranceMapper;
import com.example.ebanking.insurance.db.entity.Insurance;
import com.example.ebanking.insurance.dto.InsuranceCreateRequest;
import com.example.ebanking.insurance.dto.InsuranceResponse;
import com.example.ebanking.insurance.service.InsuranceService;
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

import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InsuranceController.class)
public class InsuranceControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InsuranceService insuranceService;

    @MockBean
    private InsuranceMapper insuranceMapper;

    @Test
    public void getAll_shouldPass() throws Exception {
        final Insurance insurance = createInsurance();
        Page<Insurance> insurancePage = new PageImpl<>(List.of(insurance));
        given(insuranceService.getAll(PageRequest.of(0,100))).willReturn(insurancePage);

        final InsuranceResponse response = createInsuranceResponse();
        given(insuranceMapper.map(insurance)).willReturn(response);

        mvc.perform(get("/insurance?page=0&limit=100")
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(1L))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].insuranceId").value(response.getInsuranceId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].type").value(response.getType()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].note").value(response.getNote()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].price").value(response.getPrice()));
    }

    @Test
    public void getByIds_shouldPass() throws Exception {
        final Insurance insurance = createInsurance();
        given(insuranceService.getByIds(Set.of(1L))).willReturn(List.of(insurance));

        final InsuranceResponse response = createInsuranceResponse();
        given(insuranceMapper.map(insurance)).willReturn(response);

        mvc.perform(post("/insurance/getByIds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Set.of(1L))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].insuranceId").value(response.getInsuranceId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value(response.getType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].note").value(response.getNote()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(response.getPrice()));
    }

    @Test
    public void getById_shouldPass() throws Exception {
        final Insurance insurance = createInsurance();
        given(insuranceService.getById(1L)).willReturn(insurance);

        final InsuranceResponse response = createInsuranceResponse();
        given(insuranceMapper.map(insurance)).willReturn(response);

        mvc.perform(get("/insurance/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.insuranceId").value(response.getInsuranceId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(response.getType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.note").value(response.getNote()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(response.getPrice()));
    }

    @Test
    public void create_shouldPass() throws Exception {
        InsuranceCreateRequest request = createInsuranceRequest();
        Insurance insurance = createInsurance();
        InsuranceResponse response = createInsuranceResponse();

        given(insuranceService.create(request)).willReturn(insurance);
        given(insuranceMapper.map(insurance)).willReturn(response);

        mvc.perform(post("/insurance/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(response.getType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.note").value(response.getNote()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(response.getPrice()));
    }

    @Test
    public void delete_shouldPass() throws Exception {
        mvc.perform(delete("/insurance/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private Insurance createInsurance() {
        Insurance insurance = new Insurance();
        insurance.setType("Car");
        insurance.setNote("No note");
        insurance.setPrice(9000);
        return  insurance;
    }

    private InsuranceResponse createInsuranceResponse() {
        return new InsuranceResponse(
                1L,
                "Car",
                "No note",
                9000
        );
    }

    private InsuranceCreateRequest createInsuranceRequest() {
        return new InsuranceCreateRequest(
                "Car",
                "No note",
                9000
        );
    }

    }
