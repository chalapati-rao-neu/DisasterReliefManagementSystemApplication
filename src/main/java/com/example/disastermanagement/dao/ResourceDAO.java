package com.example.disastermanagement.dao;

import com.example.disastermanagement.models.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceDAO {

    private final SessionFactory sessionFactory;

 
    public ResourceDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Resource resource) {
        getCurrentSession().persist(resource);
    }

    public Resource findById(Long id) {
        return getCurrentSession().get(Resource.class, id);
    }

    public List<Resource> findAll() {
        return getCurrentSession()
                .createQuery("from Resource", Resource.class)
                .list();
    }

    public void update(Resource resource) {
        getCurrentSession().merge(resource);
    }

    public void delete(Resource resource) {
        getCurrentSession().remove(resource);
    }

    public void deleteById(Long id) {
        Resource resource = findById(id);
        if (resource != null) {
            delete(resource);
        }
    }
}