package com.vehicleregistration.vehicle_registration_system.Repos;


import com.vehicleregistration.vehicle_registration_system.Entities.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

    // Find license by vehicle id
    Optional<License> findByVehicleId(Long vehicleId);

    // Find all expired licenses — used for the expired licenses endpoint
    List<License> findByExpiryDateBeforeAndActiveTrue(LocalDate date);

    // Check if vehicle already has a license
    boolean existsByVehicleId(Long vehicleId);
}
