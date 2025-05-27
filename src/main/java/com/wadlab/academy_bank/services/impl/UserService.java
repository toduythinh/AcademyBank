package com.wadlab.academy_bank.services.impl;

import java.util.List;
import com.wadlab.academy_bank.dto.BankResponse;
import com.wadlab.academy_bank.dto.UserRequest;
import com.wadlab.academy_bank.dto.UserSummary;
import com.wadlab.academy_bank.entity.User;

public interface UserService {
    public BankResponse createAccount(UserRequest userRequest);
    
    List<UserSummary> getAllUsers();

    User getUserById(Long id);

    BankResponse updateUser(Long id, UserRequest userRequest);

    BankResponse deleteUser(Long id);
}
