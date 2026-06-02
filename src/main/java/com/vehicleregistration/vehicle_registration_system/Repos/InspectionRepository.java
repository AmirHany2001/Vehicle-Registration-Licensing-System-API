package com.vehicleregistration.vehicle_registration_system.Repos;



import com.vehicleregistration.vehicle_registration_system.Entities.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {

    // Get all inspections for a vehicle
    List<Inspection> findByVehicleId(Long vehicleId);

    // Get the latest inspection for a vehicle
    // Used to check validity before license renewal
    Optional<Inspection> findTopByVehicleIdOrderByInspectionDateDesc(Long vehicleId);

    // Get vehicles with inspections due soon — nextDueDate is before a given date
    List<Inspection> findByNextDueDateBefore(LocalDate date);

    // Count upcoming inspections due within 30 days
    long countByNextDueDateBefore(LocalDate date);
}