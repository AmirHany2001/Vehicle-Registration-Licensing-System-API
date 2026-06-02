package com.vehicleregistration.vehicle_registration_system.Repos;


import com.vehicleregistration.vehicle_registration_system.Entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Check if plate number already exists — used before registering
    boolean existsByPlateNumber(String plateNumber);

    // Get all vehicles of a specific owner
    List<Vehicle> findByOwnerId(Long ownerId);

    // Count vehicles by status
    long countByStatus(Vehicle.VehicleStatus status);
}