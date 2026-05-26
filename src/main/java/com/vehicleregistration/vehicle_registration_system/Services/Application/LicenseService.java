package com.vehicleregistration.vehicle_registration_system.Services.Application;


import com.vehicleregistration.vehicle_registration_system.Models.BusinessLogicException;
import com.vehicleregistration.vehicle_registration_system.Models.NotFoundException;
import com.vehicleregistration.vehicle_registration_system.DTOs.LicenseDto;
import com.vehicleregistration.vehicle_registration_system.Mapper.LicenseMapper;
import com.vehicleregistration.vehicle_registration_system.Entities.Inspection;
import com.vehicleregistration.vehicle_registration_system.Entities.License;
import com.vehicleregistration.vehicle_registration_system.Entities.Vehicle;
import com.vehicleregistration.vehicle_registration_system.Repos.InspectionRepository;
import com.vehicleregistration.vehicle_registration_system.Repos.LicenseRepository;
import com.vehicleregistration.vehicle_registration_system.Repos.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final VehicleRepository vehicleRepository;
    private final InspectionRepository inspectionRepository;
    private final FineService fineService;
    private final LicenseMapper licenseMapper;

    // ── Get license by vehicle ────────────────────────────

    public LicenseDto.Response getByVehicleId(Long vehicleId) {
        License license = licenseRepository.findByVehicleId(vehicleId)
                .orElseThrow(() -> new NotFoundException("license.not.found", vehicleId));
        return licenseMapper.toResponse(license);
    }

    // ── Renew license ─────────────────────────────────────
    // This is the most important method — contains all business rules

    public LicenseDto.Response renew(Long vehicleId) {

        // 1. Check vehicle exists and is APPROVED
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("vehicle.not.found", vehicleId));

        if (vehicle.getStatus() != Vehicle.VehicleStatus.APPROVED) {
            throw new BusinessLogicException("vehicle.not.approved");
        }

        // 2. Check inspection exists
        Inspection latestInspection = inspectionRepository
                .findTopByVehicleIdOrderByInspectionDateDesc(vehicleId)
                .orElseThrow(() -> new BusinessLogicException("license.inspection.not.found"));

        // 3. Check inspection passed
        if (!latestInspection.isPassed()) {
            throw new BusinessLogicException("license.inspection.failed");
        }

        // 4. Check inspection is within 6 months
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        if (latestInspection.getInspectionDate().isBefore(sixMonthsAgo)) {
            throw new BusinessLogicException("license.inspection.invalid");
        }

        // 5. Check if license already exists
        License license = licenseRepository.findByVehicleId(vehicleId)
                .orElse(new License()); // create new if first time

        // 6. If license exists and is still active — cannot renew yet
        if (license.getId() != null &&
                license.getExpiryDate().isAfter(LocalDate.now())) {
            throw new BusinessLogicException("license.already.active");
        }

        // 7. If license is expired — generate fine automatically
        if (license.getId() != null &&
                license.getExpiryDate().isBefore(LocalDate.now())) {
            fineService.generateLateFine(vehicle);
        }

        // 8. Set new dates — issue today, expires in 1 year
        license.setVehicle(vehicle);
        license.setIssueDate(LocalDate.now());
        license.setExpiryDate(LocalDate.now().plusYears(1));
        license.setActive(true);

        return licenseMapper.toResponse(licenseRepository.save(license));
    }

    // ── Get all expired licenses ──────────────────────────

    public List<LicenseDto.Response> getExpired() {
        return licenseRepository
                .findByExpiryDateBeforeAndActiveTrue(LocalDate.now())
                .stream()
                .map(licenseMapper::toResponse)
                .collect(Collectors.toList());
    }
}