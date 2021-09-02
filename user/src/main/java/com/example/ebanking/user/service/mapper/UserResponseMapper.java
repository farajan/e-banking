package com.example.ebanking.user.service.mapper;

import com.example.ebanking.user.db.entity.User;
import com.example.ebanking.user.dto.BankAccountResponse;
import com.example.ebanking.user.dto.InsuranceResponse;
import com.example.ebanking.user.dto.UserResponse;
import com.example.ebanking.user.service.BankAccountService;
import com.example.ebanking.user.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserResponseMapper implements Mapper<User, UserResponse> {

    private final InsuranceService insuranceService;
    private final BankAccountService bankAccountService;

    @Override
    public User mapToEntity(UserResponse dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setBirthday(dto.getBirthday());
        return user;
    }

    @Override
    public UserResponse mapFromEntity(User entity) {
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
        return bankAccountIds.isEmpty() ? Collections.emptyList() : bankAccountService.getByIds(bankAccountIds);
    }

    private List<InsuranceResponse> getInsuranceList(User user) {
        Set<Long> insuranceIds = user.getInsuranceIds();
        return insuranceIds.isEmpty() ? Collections.emptyList() : insuranceService.getByIds(insuranceIds);
    }

}
