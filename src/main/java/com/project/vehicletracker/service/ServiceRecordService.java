package com.project.vehicletracker.service;

import com.project.vehicletracker.entity.*;
import com.project.vehicletracker.enums.ServiceStatus;
import com.project.vehicletracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRecordService {

    @Autowired
    private ServiceRecordRepository recordRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private ServiceTypeRepository typeRepo;

    @Autowired
    private UserRepository userRepo;

    public User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ServiceRecord createServiceRequest(ServiceRecord record) {

        Vehicle vehicle = vehicleRepo.findById(record.getVehicle().getId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        ServiceType type = typeRepo.findById(record.getServiceType().getId())
                .orElseThrow(() -> new RuntimeException("Service type not found"));

        User user = getCurrentUser();

        if (!vehicle.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        record.setVehicle(vehicle);
        record.setServiceType(type);

        record.setUser(user);
        record.setStatus(ServiceStatus.PENDING);
        record.setCost(null); // user cannot set
        record.setServiceDate(java.time.LocalDate.now());
        return recordRepo.save(record);
    }

    public List<ServiceRecord> getMyServiceRecords() {
        User user = getCurrentUser();
        return recordRepo.findByVehicleUserId(user.getId());
    }
    public ServiceRecord updateRecord(Long id, ServiceRecord input) {

        ServiceRecord record = recordRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (!record.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized");
        }

        if (record.getStatus() != ServiceStatus.PENDING) {
            throw new RuntimeException("Service already completed, cannot update");
        }

        record.setDescription(input.getDescription());

        return recordRepo.save(record);
    }

    public List<ServiceRecord> getAllRecords() {
        return recordRepo.findAll();
    }

    public ServiceRecord markCompleted(Long id, Double cost) {

        ServiceRecord record = recordRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setStatus(ServiceStatus.COMPLETED);
        record.setCost(cost);

        return recordRepo.save(record);
    }

    public void deleteRecord(Long id) {
        recordRepo.deleteById(id);
    }
}