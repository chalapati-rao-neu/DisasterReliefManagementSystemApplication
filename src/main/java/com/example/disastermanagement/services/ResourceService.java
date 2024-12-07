package com.example.disastermanagement.services;

import com.example.disastermanagement.dao.ResourceDAO;
import com.example.disastermanagement.models.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceService {

    private final ResourceDAO resourceDAO;

  
    public ResourceService(ResourceDAO resourceDAO) {
        this.resourceDAO = resourceDAO;
    }

    public void createResource(Resource resource) {
        resourceDAO.save(resource);
    }

    public Resource getResourceById(Long id) {
        return resourceDAO.findById(id);
    }

    public List<Resource> getAllResources() {
        return resourceDAO.findAll();
    }

    public void updateResource(Resource resource) {
        resourceDAO.update(resource);
    }

    public void deleteResourceById(Long id) {
        resourceDAO.deleteById(id);
    }
}