package com.project.vehicletracker.controller;

import com.project.vehicletracker.entity.ServiceRecord;
import com.project.vehicletracker.service.ServiceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceRecordController {

    @Autowired
    private ServiceRecordService recordService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ServiceRecord> addRecord(@RequestBody ServiceRecord record) {
        return ResponseEntity.ok(recordService.createServiceRequest(record));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public ResponseEntity<List<ServiceRecord>> getMyRecords() {
        return ResponseEntity.ok(recordService.getMyServiceRecords());
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(
            @PathVariable Long id,
            @RequestBody ServiceRecord details) {
        try {
            return ResponseEntity.ok(recordService.updateRecord(id, details));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ServiceRecord>> getAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/complete")
    public ResponseEntity<ServiceRecord> markCompleted(
            @PathVariable Long id,
            @RequestParam Double cost) {

        return ResponseEntity.ok(recordService.markCompleted(id, cost));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}