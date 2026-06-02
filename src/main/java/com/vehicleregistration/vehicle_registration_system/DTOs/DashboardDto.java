package com.vehicleregistration.vehicle_registration_system.DTOs;


import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class DashboardDto {

    // ── Users ─────────────────────────────────────────────
    private long totalUsers;

    // ── Vehicles ──────────────────────────────────────────
    private long totalVehicles;
    private long pendingVehicles;
    private long approvedVehicles;
    private long rejectedVehicles;

    // ── Licenses ──────────────────────────────────────────
    private long totalLicenses;
    private long activeLicenses;
    private long expiredLicenses;

    // ── Inspections ───────────────────────────────────────
    private long totalInspections;
    private long upcomingInspections;

    // ── Fines ─────────────────────────────────────────────
    private long totalFines;
    private long unpaidFines;
    private long paidFines;
    private BigDecimal totalFineAmount;
    private BigDecimal unpaidFineAmount;
}
