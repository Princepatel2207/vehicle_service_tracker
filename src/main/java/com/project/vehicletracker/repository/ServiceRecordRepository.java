package com.project.vehicletracker.repository;

import com.project.vehicletracker.entity.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {

    List<ServiceRecord> findByVehicleUserId(Long userId);
}