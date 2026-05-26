package com.vehicleregistration.vehicle_registration_system.Entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "inspections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate inspectionDate;

    @Column(nullable = false)
    private LocalDate nextDueDate;   // always inspectionDate + 6 months, set in service

    @Column(nullable = false)
    private boolean passed;

    private String notes;

    // Many inspections can belong to one vehicle (history of inspections)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
}