package com.vehicleregistration.vehicle_registration_system.Entities;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;    // always BigDecimal for money, never double

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private LocalDate issuedDate;

    @Column(nullable = false)
    private boolean paid = false;

    // Many fines can belong to one vehicle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
}
