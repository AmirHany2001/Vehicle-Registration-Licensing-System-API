package com.vehicleregistration.vehicle_registration_system.Services.Application;

import com.vehicleregistration.vehicle_registration_system.DTOs.DashboardDto;
import com.vehicleregistration.vehicle_registration_system.Entities.Vehicle.VehicleStatus;
import com.vehicleregistration.vehicle_registration_system.Repos.FineRepository;
import com.vehicleregistration.vehicle_registration_system.Repos.InspectionRepository;
import com.vehicleregistration.vehicle_registration_system.Repos.LicenseRepository;
import com.vehicleregistration.vehicle_registration_system.Repos.UserRepository;
import com.vehicleregistration.vehicle_registration_system.Repos.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final LicenseRepository licenseRepository;
    private final InspectionRepository inspectionRepository;
    private final FineRepository fineRepository;

    public DashboardDto getSummary() {

        // ── Users ─────────────────────────────────────────
        long totalUsers = userRepository.count();

        // ── Vehicles ──────────────────────────────────────
        long totalVehicles = vehicleRepository.count();
        long pendingVehicles = vehicleRepository
                .countByStatus(VehicleStatus.PENDING);
        long approvedVehicles = vehicleRepository
                .countByStatus(VehicleStatus.APPROVED);
        long rejectedVehicles = vehicleRepository
                .countByStatus(VehicleStatus.REJECTED);

        // ── Licenses ──────────────────────────────────────
        long totalLicenses = licenseRepository.count();
        long activeLicenses = licenseRepository.countByActiveTrue();
        long expiredLicenses = licenseRepository
                .countByExpiryDateBeforeAndActiveTrue(LocalDate.now());

        // ── Inspections ───────────────────────────────────
        long totalInspections = inspectionRepository.count();
        long upcomingInspections = inspectionRepository
                .countByNextDueDateBefore(LocalDate.now().plusDays(30));

        // ── Fines ─────────────────────────────────────────
        long totalFines = fineRepository.count();
        long unpaidFines = fineRepository.countByPaidFalse();
        long paidFines = fineRepository.countByPaidTrue();
        BigDecimal totalFineAmount = fineRepository.sumAllFines();
        BigDecimal unpaidFineAmount = fineRepository.sumUnpaidFines();

        // ── Build and return ───────────────────────────────
        return DashboardDto.builder()
                .totalUsers(totalUsers)
                .totalVehicles(totalVehicles)
                .pendingVehicles(pendingVehicles)
                .approvedVehicles(approvedVehicles)
                .rejectedVehicles(rejectedVehicles)
                .totalLicenses(totalLicenses)
                .activeLicenses(activeLicenses)
                .expiredLicenses(expiredLicenses)
                .totalInspections(totalInspections)
                .upcomingInspections(upcomingInspections)
                .totalFines(totalFines)
                .unpaidFines(unpaidFines)
                .paidFines(paidFines)
                .totalFineAmount(totalFineAmount)
                .unpaidFineAmount(unpaidFineAmount)
                .build();
    }
}