package com.project.vehicletracker.controller;

import com.project.vehicletracker.entity.ServiceType;
import com.project.vehicletracker.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService typeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServiceType> createType(@RequestBody ServiceType type) {
        return ResponseEntity.ok(typeService.createType(type));
    }

    @GetMapping
    public ResponseEntity<List<ServiceType>> getAllTypes() {
        return ResponseEntity.ok(typeService.getAllTypes());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceType> updateType(@PathVariable Long id, @RequestBody ServiceType details) {
        return ResponseEntity.ok(typeService.updateType(id, details));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
        return ResponseEntity.ok("Service type removed.");
    }
}