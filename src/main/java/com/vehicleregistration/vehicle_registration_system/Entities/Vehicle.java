package com.vehicleregistration.vehicle_registration_system.Entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String plateNumber;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status = VehicleStatus.PENDING;

    // Many vehicles belong to one owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // One vehicle has one license
    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private License license;

    public enum VehicleStatus {
        PENDING, APPROVED, REJECTED
    }
}