package com.example.disastermanagement.dao;

import com.example.disastermanagement.models.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Task task) {
        getCurrentSession().persist(task);
    }

    public Task findById(Long id) {
        return getCurrentSession().get(Task.class, id);
    }

    public List<Task> findAll() {
        return getCurrentSession()
                .createQuery("from Task", Task.class)
                .list();
    }

    public void update(Task task) {
        getCurrentSession().merge(task);
    }
}