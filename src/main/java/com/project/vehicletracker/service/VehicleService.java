package com.project.vehicletracker.service;

import com.project.vehicletracker.entity.User;
import com.project.vehicletracker.entity.Vehicle;
import com.project.vehicletracker.repository.UserRepository;
import com.project.vehicletracker.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepo;

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

    public Vehicle addVehicle(Vehicle vehicle) {
        User user = getCurrentUser();
        vehicle.setUser(user);
        return vehicleRepo.save(vehicle);
    }

    public List<Vehicle> getMyVehicles() {
        User user = getCurrentUser();
        return vehicleRepo.findByUser(user);
    }

    public Vehicle updateVehicle(Long id, Vehicle details) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        User user = getCurrentUser();

        if (!vehicle.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        vehicle.setModel(details.getModel());
        vehicle.setVehicleNumber(details.getVehicleNumber());

        return vehicleRepo.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        User user = getCurrentUser();

        if (!vehicle.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        vehicleRepo.delete(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }
}