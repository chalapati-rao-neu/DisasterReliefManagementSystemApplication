package com.example.disastermanagement.dao;

import com.example.disastermanagement.models.Role;
import com.example.disastermanagement.models.User;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private final SessionFactory sessionFactory;

   
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public void save(User user) {
        getCurrentSession().persist(user); // Use persist for saving a new entity
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return getCurrentSession().get(User.class, id); // Fetch entity by primary key
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return getCurrentSession()
                .createQuery("FROM User", User.class) // HQL query to fetch all users
                .getResultList();
    }

    @Override
    @Transactional
    public void update(User user) {
        getCurrentSession().merge(user); // Merge updates into the persistence context
    }

    @Override
    @Transactional
    public void delete(User user) {
        User managedUser = getCurrentSession().contains(user) ? user : getCurrentSession().merge(user);
        getCurrentSession().remove(managedUser); // Ensure the entity is managed before removal
    }
    
    @Override
    public List<User> findUsersByRole(Role role) {
        return getCurrentSession()
                .createQuery("from User where role = :role", User.class)
                .setParameter("role", role)
                .list();
    }
}