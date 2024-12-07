package com.example.disastermanagement.dao;

import com.example.disastermanagement.models.ReliefRequest;
import com.example.disastermanagement.models.ReliefRequestStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReliefRequestDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public ReliefRequestDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Centralized method to get the current Hibernate session.
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save a new ReliefRequest (uses Hibernate persist).
     */
    public void save(ReliefRequest reliefRequest) {
        getCurrentSession().persist(reliefRequest);
    }

    /**
     * Update an existing ReliefRequest (uses Hibernate merge).
     * Returns the managed entity after merging.
     */
    public ReliefRequest update(ReliefRequest reliefRequest) {
        return getCurrentSession().merge(reliefRequest);
    }

    /**
     * Delete a ReliefRequest entity by its object (uses Hibernate remove).
     */
    public void delete(ReliefRequest reliefRequest) {
        getCurrentSession().remove(reliefRequest);
    }

    /**
     * Retrieve all ReliefRequests.
     */
    public List<ReliefRequest> findAll() {
        return getCurrentSession()
                .createQuery("from ReliefRequest", ReliefRequest.class)
                .list();
    }
    /**
     * Find all ReliefRequests with status as PENDING.
     */
    
    public List<ReliefRequest> findPendingRequests() {
        return getCurrentSession()
                .createQuery("from ReliefRequest where status = :status", ReliefRequest.class)
                .setParameter("status", ReliefRequestStatus.PENDING)
                .list();
    }

    /**
     * Find a ReliefRequest by its ID.
     */
    public ReliefRequest findById(Long id) {
        return getCurrentSession().get(ReliefRequest.class, id);
    }

    /**
     * Find ReliefRequests by their status.
     */
    public List<ReliefRequest> findByStatus(ReliefRequestStatus status) {
        return getCurrentSession()
                .createQuery("from ReliefRequest where status = :status", ReliefRequest.class)
                .setParameter("status", status)
                .list();
    }

    /**
     * Delete a ReliefRequest by its ID.
     */
    public void deleteById(Long id) {
        ReliefRequest reliefRequest = findById(id);
        if (reliefRequest != null) {
            delete(reliefRequest);
        }
    }
}