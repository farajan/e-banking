package com.example.ebanking.user.service;

import com.example.ebanking.user.db.entity.User;
import com.example.ebanking.user.dto.UserRequest;
import com.example.ebanking.user.dto.UserResponse;
import com.example.ebanking.user.service.mapper.UserMapper;
import com.example.ebanking.user.db.repository.UserRepository;
import com.example.ebanking.user.service.webClient.BankWebClient;
import com.example.ebanking.user.service.webClient.InsuranceWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final InsuranceWebClient insuranceWebClient;
    private final BankWebClient bankWebClient;
    private final UserMapper userMapper;

    public List<UserResponse> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse getById(long id) {
        User user = getUser(id);
        return userMapper.map(user);
    }

    @Transactional
    public UserResponse create(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setBirthday(userRequest.getBirthday());

        User userDB = userRepository.save(user);
        return userMapper.map(userDB);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponse addBankAccount(long userId, long bankAccountId) {
        User user = getUser(userId);
        if (bankWebClient.findById(bankAccountId) == null) {
            throw new IllegalArgumentException("Bank account with id: " + bankAccountId + " doesn't exists.");
        }
        user.getBankAccountIds().add(bankAccountId);
        userRepository.save(user);
        return getById(userId);
    }

    @Transactional
    public UserResponse deleteBankAccount(long userId, long bankAccountId) {
        User user = getUser(userId);
        user.getBankAccountIds().remove(bankAccountId);
        userRepository.save(user);
        return getById(userId);
    }

    @Transactional
    public UserResponse addInsurance(long userId, long insuranceId) {
        User user = getUser(userId);
        if (insuranceWebClient.findById(insuranceId) == null) {
            throw new IllegalArgumentException("Insurance with id: " + insuranceId + " doesn't exists.");
        }
        user.getInsuranceIds().add(insuranceId);
        userRepository.save(user);
        return getById(userId);
    }

    @Transactional
    public UserResponse deleteInsurance(long userId, long insuranceId) {
        User user = getUser(userId);
        user.getInsuranceIds().remove(insuranceId);
        userRepository.save(user);
        return getById(userId);
    }

    public boolean isBankAccountUsed(long bankAccountId) {
        return !userRepository.getUsersByBankAccountIds(bankAccountId).isEmpty();
    }

    public boolean isInsuranceUsed(long insuranceId) {
        return !userRepository.getUsersByInsuranceIds(insuranceId).isEmpty();
    }

    private User getUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " doesn't exist."));
    }
}
