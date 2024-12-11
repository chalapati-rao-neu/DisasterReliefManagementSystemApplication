package com.example.disastermanagement.services;

import com.example.disastermanagement.models.Role;
import com.example.disastermanagement.models.User;

import java.util.List;

public interface UserService {
    void createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(Long id);
    List<User> getUsersByRole(Role role);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
}