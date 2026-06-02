package com.vehicleregistration.vehicle_registration_system.Repos;



import com.vehicleregistration.vehicle_registration_system.Entities.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

    // Get all fines for a specific vehicle
    List<Fine> findByVehicleId(Long vehicleId);

    // Get all fines for a specific owner
    List<Fine> findByVehicleOwnerId(Long ownerId);

    // Count unpaid fines
    long countByPaidFalse();

    // Count paid fines
    long countByPaidTrue();

    // Sum all fine amounts
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f")
    BigDecimal sumAllFines();

    // Sum unpaid fine amounts
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Fine f WHERE f.paid = false")
    BigDecimal sumUnpaidFines();
}
