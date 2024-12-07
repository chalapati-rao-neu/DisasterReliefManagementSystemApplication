package com.example.disastermanagement.services;

import com.example.disastermanagement.dao.UserDAO;
import com.example.disastermanagement.models.Role;
import com.example.disastermanagement.models.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userDAO.save(user); // Delegate the save operation to the DAO
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + id); // Handle not-found case
        }
        return user;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.findAll(); // Fetch all users
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (userDAO.findById(user.getId()) == null) {
            throw new RuntimeException("Cannot update. User not found with ID: " + user.getId());
        }
        userDAO.update(user); // Update the user
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new RuntimeException("Cannot delete. User not found with ID: " + id);
        }
        userDAO.delete(user); // Delete the user
    }
    @Transactional
    @Override
    public List<User> getUsersByRole(Role role) {
        return userDAO.findUsersByRole(role);
    }
}