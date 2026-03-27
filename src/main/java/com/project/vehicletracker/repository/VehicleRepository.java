package com.project.vehicletracker.repository;

import com.project.vehicletracker.entity.Vehicle;
import com.project.vehicletracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUser(User user);
}