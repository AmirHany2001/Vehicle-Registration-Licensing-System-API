package com.vehicleregistration.vehicle_registration_system.Repos;



import com.vehicleregistration.vehicle_registration_system.Entities.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

    // Get all fines for a specific vehicle
    List<Fine> findByVehicleId(Long vehicleId);

    // Get all fines for a specific owner
    List<Fine> findByVehicleOwnerId(Long ownerId);

    // Get all unpaid fines for a vehicle
    List<Fine> findByVehicleIdAndPaidFalse(Long vehicleId);
}
