package com.wadlab.academy_bank.controller;

import com.wadlab.academy_bank.dto.BankResponse;
import com.wadlab.academy_bank.dto.UserRequest;
import com.wadlab.academy_bank.dto.UserSummary;
import com.wadlab.academy_bank.entity.User;
import com.wadlab.academy_bank.services.impl.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    // POST /api/user - Create a new user account
    @PostMapping("/user")
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    // GET /api/users - Get all users (id and name)
    @GetMapping("/users")
    public List<UserSummary> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/user/{id} - Get details of a specific user
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // PUT /api/user/{id} - Update a specific user
    @PutMapping("/user/{id}")
    public BankResponse updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    // DELETE /api/user/{id} - Delete a specific user
    @DeleteMapping("/user/{id}")
    public BankResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
