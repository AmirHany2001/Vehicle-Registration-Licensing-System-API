package com.vehicleregistration.vehicle_registration_system.Repos;



import com.vehicleregistration.vehicle_registration_system.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check if email already exists — used before saving new user
    boolean existsByEmailIgnoreCase(String email);

    // Check if national ID already exists
    boolean existsByNationalId(String nationalId);

    // Find user by email — used in login
    Optional<User> findByEmailIgnoreCase(String email);

}
