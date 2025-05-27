package com.wadlab.academy_bank.services.impl;

import com.wadlab.academy_bank.dto.AccountInfo;
import com.wadlab.academy_bank.dto.BankResponse;
import com.wadlab.academy_bank.dto.UserRequest;
import com.wadlab.academy_bank.dto.UserSummary;
import com.wadlab.academy_bank.entity.User;
import com.wadlab.academy_bank.repository.UserRepository;
import com.wadlab.academy_bank.utils.AccountUtilis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responeCode(AccountUtilis.ACCOUNT_EXISTS_CODE)
                    .responeMessage(AccountUtilis.ACCOUNT_EXISTS_MESSAGE)
                    .AccountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtilis.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .email(userRequest.getEmail())
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(newUser);

        AccountInfo accountInfo = AccountInfo.builder()
                .accountBalance(savedUser.getAccountBalance())
                .accountNumber(savedUser.getAccountNumber())
                .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                .build();

        return BankResponse.builder()
                .responeCode(AccountUtilis.ACCOUNT_CREATION_SUCCESS_CODE)
                .responeMessage(AccountUtilis.ACCOUNT_CREATION_SUCCESS_MASSAGE)
                .AccountInfo(accountInfo)
                .build();
    }

    // GET all users - return id and fullName only
    @Override
    public List<UserSummary> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map(user -> UserSummary.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build())
            .toList();
    }


    // GET user by id
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found with ID: " + id));
    }

    // UPDATE user
    @Override
    public BankResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found with ID: " + id));

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setOtherName(userRequest.getOtherName());
        user.setAddress(userRequest.getAddress());
        user.setStateOfOrigin(userRequest.getStateOfOrigin());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAlternativePhoneNumber(userRequest.getAlternativePhoneNumber());
        user.setEmail(userRequest.getEmail());

        User updatedUser = userRepository.save(user);

        AccountInfo accountInfo = AccountInfo.builder()
                .accountBalance(updatedUser.getAccountBalance())
                .accountNumber(updatedUser.getAccountNumber())
                .accountName(updatedUser.getFirstName() + " " + updatedUser.getLastName() + " " + updatedUser.getOtherName())
                .build();

        return BankResponse.builder()
                .responeCode("200")
                .responeMessage("User updated successfully")
                .AccountInfo(accountInfo)
                .build();
    }

    // DELETE user
    @Override
    public BankResponse deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return BankResponse.builder()
                .responeCode("404")
                .responeMessage("User not found")
                .AccountInfo(null)
                .build();
        }

        userRepository.delete(user);

        return BankResponse.builder()
            .responeCode("200")
            .responeMessage("User deleted successfully")
            .AccountInfo(null)
            .build();
    }

}
