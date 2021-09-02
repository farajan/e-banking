package com.example.ebanking.user.service;

import com.example.ebanking.user.db.entity.User;
import com.example.ebanking.user.dto.UserRequest;
import com.example.ebanking.user.db.repository.UserRepository;
import com.example.ebanking.user.service.webClient.BankWebClient;
import com.example.ebanking.user.service.webClient.InsuranceWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final InsuranceWebClient insuranceWebClient;
    private final BankWebClient bankWebClient;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User getById(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " doesn't exist."));
    }

    @Transactional
    public User create(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setBirthday(userRequest.getBirthday());

        return userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User addBankAccount(long userId, long bankAccountId) {
        User user = getById(userId);
        if (bankWebClient.findById(bankAccountId) == null) {
            throw new IllegalArgumentException("Bank account with id: " + bankAccountId + " doesn't exists.");
        }
        user.getBankAccountIds().add(bankAccountId);
        return userRepository.save(user);
    }

    @Transactional
    public User deleteBankAccount(long userId, long bankAccountId) {
        User user = getById(userId);
        user.getBankAccountIds().remove(bankAccountId);
        return userRepository.save(user);
    }

    @Transactional
    public User addInsurance(long userId, long insuranceId) {
        User user = getById(userId);
        if (insuranceWebClient.findById(insuranceId) == null) {
            throw new IllegalArgumentException("Insurance with id: " + insuranceId + " doesn't exists.");
        }
        user.getInsuranceIds().add(insuranceId);
        return userRepository.save(user);
    }

    @Transactional
    public User deleteInsurance(long userId, long insuranceId) {
        User user = getById(userId);
        user.getInsuranceIds().remove(insuranceId);
        return userRepository.save(user);
    }

    public boolean isBankAccountUsed(long bankAccountId) {
        return userRepository.existsUsersByBankAccountIds(bankAccountId);
    }

    public boolean isInsuranceUsed(long insuranceId) {
        return userRepository.existsUsersByInsuranceIds(insuranceId);
    }
}
