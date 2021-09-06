package com.example.ebanking.user.service;

import com.example.ebanking.user.db.entity.User;
import com.example.ebanking.user.dto.UserCreateRequest;
import com.example.ebanking.user.db.repository.UserRepository;
import com.example.ebanking.user.service.webclient.BankAccountWebClient;
import com.example.ebanking.user.service.webclient.InsuranceWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final InsuranceWebClient insuranceWebClient;
    private final BankAccountWebClient bankAccountWebClient;
    private final PasswordEncoder passwordEncoder;

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public User getById(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " doesn't exist."));
    }

    @Transactional
    public User create(@Valid UserCreateRequest userCreateRequest) {
        User user = new User();
        user.setFirstName(userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName());
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setEmail(userCreateRequest.getEmail());
        user.setBirthday(userCreateRequest.getBirthday());

        return userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User addBankAccount(long userId, long bankAccountId) {
        User user = getById(userId);
        if (bankAccountWebClient.getById(bankAccountId) == null) {
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
        if (insuranceWebClient.getById(insuranceId) == null) {
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
