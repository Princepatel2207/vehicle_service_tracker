package com.project.vehicletracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.vehicletracker.enums.ServiceStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "service_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate serviceDate;

    private Double cost;
    private String description;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    @JsonIgnoreProperties({"user","serviceRecords"})
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}