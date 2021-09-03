package com.example.ebanking.user.controller.mapper;

import com.example.ebanking.user.db.entity.User;
import com.example.ebanking.user.dto.BankAccountResponse;
import com.example.ebanking.user.dto.InsuranceResponse;
import com.example.ebanking.user.dto.UserResponse;
import com.example.ebanking.user.service.webclient.BankWebClient;
import com.example.ebanking.user.service.webclient.InsuranceWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserResponse> {

    private final InsuranceWebClient insuranceWebClient;
    private final BankWebClient bankWebClient;

    @Override
    public UserResponse map(User entity) {
        return new UserResponse(
                entity.getUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getBirthday(),
                getBankAccountList(entity),
                getInsuranceList(entity)
        );
    }

    private List<BankAccountResponse> getBankAccountList(User user) {
        Set<Long> bankAccountIds = user.getBankAccountIds();
        return bankAccountIds.isEmpty() ? Collections.emptyList() : bankWebClient.getByIds(bankAccountIds);
    }

    private List<InsuranceResponse> getInsuranceList(User user) {
        Set<Long> insuranceIds = user.getInsuranceIds();
        return insuranceIds.isEmpty() ? Collections.emptyList() : insuranceWebClient.getByIds(insuranceIds);
    }

}
