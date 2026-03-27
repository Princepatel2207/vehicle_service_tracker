package com.project.vehicletracker.service;

import com.project.vehicletracker.entity.ServiceType;
import com.project.vehicletracker.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceTypeService {
    @Autowired
    private ServiceTypeRepository typeRepo;

    public ServiceType createType(ServiceType type) { return typeRepo.save(type); }

    public List<ServiceType> getAllTypes() { return typeRepo.findAll(); }

    public ServiceType updateType(Long id, ServiceType details) {
        ServiceType type = typeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Type not found"));

        if (details.getName() != null && !details.getName().isEmpty()) {
            type.setName(details.getName());
        }

        return typeRepo.save(type);
    }

    public void deleteType(Long id) {
        ServiceType type = typeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Type not found"));

        typeRepo.delete(type);
    }
}