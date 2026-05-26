package com.vehicleregistration.vehicle_registration_system.Services.Application;


import com.vehicleregistration.vehicle_registration_system.Models.BusinessLogicException;
import com.vehicleregistration.vehicle_registration_system.Models.NotFoundException;
import com.vehicleregistration.vehicle_registration_system.DTOs.FineDto;
import com.vehicleregistration.vehicle_registration_system.Entities.Fine;
import com.vehicleregistration.vehicle_registration_system.Entities.Vehicle;
import com.vehicleregistration.vehicle_registration_system.Mapper.FineMapper;
import com.vehicleregistration.vehicle_registration_system.Repos.FineRepository;
import com.vehicleregistration.vehicle_registration_system.Repos.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineRepository fineRepository;
    private final VehicleRepository vehicleRepository;
    private final FineMapper fineMapper;

    // ── Get all fines for an owner ────────────────────────

    public List<FineDto.Response> getByOwner(Long ownerId) {
        return fineRepository.findByVehicleOwnerId(ownerId)
                .stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ── Pay a fine ────────────────────────────────────────

    public FineDto.Response pay(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new NotFoundException("fine.not.found", fineId));

        // Cannot pay an already paid fine
        if (fine.isPaid()) {
            throw new BusinessLogicException("fine.already.paid");
        }

        fine.setPaid(true);
        return fineMapper.toResponse(fineRepository.save(fine));
    }

    // ── Auto generate late renewal fine ──────────────────
    // Called internally by LicenseService when license is expired

    public void generateLateFine(Vehicle vehicle) {
        Fine fine = new Fine();
        fine.setVehicle(vehicle);
        fine.setAmount(new BigDecimal("500.00")); // fixed late renewal amount
        fine.setReason("Late license renewal");
        fine.setIssuedDate(LocalDate.now());
        fine.setPaid(false);
        fineRepository.save(fine);
    }

    // ── Get all fines for a vehicle ───────────────────────

    public List<FineDto.Response> getByVehicle(Long vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new NotFoundException("vehicle.not.found", vehicleId);
        }
        return fineRepository.findByVehicleId(vehicleId)
                .stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }
}