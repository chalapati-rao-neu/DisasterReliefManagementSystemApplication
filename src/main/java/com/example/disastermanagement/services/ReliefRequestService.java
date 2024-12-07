package com.example.disastermanagement.services;

import com.example.disastermanagement.dao.ReliefRequestDAO;
import com.example.disastermanagement.models.ReliefRequest;
import com.example.disastermanagement.models.ReliefRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReliefRequestService {

    private final ReliefRequestDAO reliefRequestDAO;

    @Autowired
    public ReliefRequestService(ReliefRequestDAO reliefRequestDAO) {
        this.reliefRequestDAO = reliefRequestDAO;
    }

    @Transactional
    public void createReliefRequest(ReliefRequest reliefRequest) {
        if (reliefRequest.getStatus() == null) {
            reliefRequest.setStatus(ReliefRequestStatus.PENDING); // Default status
        }
        reliefRequestDAO.save(reliefRequest);
    }
    
    @Transactional(readOnly = true)
    public List<ReliefRequest> getPendingRequests() {
        return reliefRequestDAO.findPendingRequests();
    }

    @Transactional(readOnly = true)
    public List<ReliefRequest> getAllReliefRequests() {
        return reliefRequestDAO.findAll();
    }

    @Transactional(readOnly = true)
    public ReliefRequest getReliefRequestById(Long id) {
        return reliefRequestDAO.findById(id);
    }

    @Transactional
    public void updateReliefRequestStatus(Long id, ReliefRequestStatus status) {
        ReliefRequest existingRequest = reliefRequestDAO.findById(id);
        if (existingRequest != null) {
            existingRequest.setStatus(status);
            reliefRequestDAO.update(existingRequest);
        }
    }
}