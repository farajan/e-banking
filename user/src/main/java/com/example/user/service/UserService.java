package com.example.user.service;

import com.example.user.db.entity.User;
import com.example.user.db.repository.UserRepository;
import com.example.user.dto.BankAccountDto;
import com.example.user.dto.InsuranceDto;
import com.example.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final InsuranceService insuranceService;
    private final BankAccountService bankAccountService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public UserDto getById(long id) {
        User user = getUser(id);

         return UserDto.builder()
                 .userId(user.getUserId())
                 .firstName(user.getFirstName())
                 .lastName(user.getLastName())
                 .username(user.getUsername())
                 .birthday(user.getBirthday())
                 .bankAccountList(getBankAccountList(user))
                 .insuranceList(getInsuranceList(user))
                 .build();
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto addBankAccount(long userId, long bankAccountId) {
        User user = getUser(userId);
        if (bankAccountService.findById(bankAccountId) == null) {
            throw new IllegalArgumentException("Bank account with id: " + bankAccountId + " doesn't exists.");
        }
        user.getBankAccountIds().add(bankAccountId);
        userRepository.save(user);
        return getById(userId);
    }

    @Transactional
    public UserDto deleteBankAccount(long userId, long bankAccountId) {
        User user = getUser(userId);
        user.getBankAccountIds().remove(bankAccountId);
        userRepository.save(user);
        return getById(userId);
    }

    @Transactional
    public UserDto addInsurance(long userId, long insuranceId) {
        User user = getUser(userId);
        if (insuranceService.findById(insuranceId) == null) {
            throw new IllegalArgumentException("Insurance with id: " + insuranceId + " doesn't exists.");
        }
        user.getInsuranceIds().add(insuranceId);
        userRepository.save(user);
        return getById(userId);
    }

    @Transactional
    public UserDto deleteInsurance(long userId, long insuranceId) {
        User user = getUser(userId);
        user.getInsuranceIds().remove(insuranceId);
        userRepository.save(user);
        return getById(userId);
    }

    private User getUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " doesn't exist."));
    }

    private List<BankAccountDto> getBankAccountList(User user) {
        return user
                .getBankAccountIds()
                .stream()
                .map(bankAccountService::findById)
                .collect(Collectors.toList());
    }

    private List<InsuranceDto> getInsuranceList(User user) {
        return user
                .getInsuranceIds()
                .stream()
                .map(insuranceService::findById)
                .collect(Collectors.toList());
    }

    public boolean isBankAccountUsed(long bankAccountId) {
        return findAll().stream().anyMatch(user -> user.getBankAccountIds().contains(bankAccountId));
    }

    public boolean isInsuranceUsed(long insuranceId) {
        return findAll().stream().anyMatch(user -> user.getInsuranceIds().contains(insuranceId));
    }
}
