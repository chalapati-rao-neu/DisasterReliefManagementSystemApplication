package com.example.disastermanagement.dao;

import com.example.disastermanagement.models.Role;
import com.example.disastermanagement.models.User;
import java.util.List;

public interface UserDAO {
    void save(User user);          // Uses persist
    User findById(Long id);        // Uses find
    List<User> findAll();          // Uses HQL
    void update(User user);        // Uses merge
    void delete(User user);        // Uses remove
    List<User> findUsersByRole(Role role);
    User findByUsername(String username);
}