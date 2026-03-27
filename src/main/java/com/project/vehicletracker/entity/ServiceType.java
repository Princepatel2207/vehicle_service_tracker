package com.project.vehicletracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_types")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
