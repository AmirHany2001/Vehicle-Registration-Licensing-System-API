package com.vehicleregistration.vehicle_registration_system.Services.Application;


import com.vehicleregistration.vehicle_registration_system.Models.NotFoundException;
import com.vehicleregistration.vehicle_registration_system.DTOs.InspectionDto;
import com.vehicleregistration.vehicle_registration_system.Mapper.InspectionMapper;
import com.vehicleregistration.vehicle_registration_system.Entities.Inspection;
import com.vehicleregistration.vehicle_registration_system.Entities.Vehicle;
import com.vehicleregistration.vehicle_registration_system.Repos.InspectionRepository;
import com.vehicleregistration.vehicle_registration_system.Repos.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final VehicleRepository vehicleRepository;
    private final InspectionMapper inspectionMapper;

    // ── Add inspection record ─────────────────────────────

    public InspectionDto.Response addInspection(InspectionDto.Request request) {

        // 1. Check vehicle exists
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new NotFoundException("vehicle.not.found",
                        request.getVehicleId()));

        // 2. Map request to entity
        Inspection inspection = inspectionMapper.toEntity(request);

        // 3. Set vehicle
        inspection.setVehicle(vehicle);

        // 4. Calculate next due date — always 6 months after inspection date
        inspection.setNextDueDate(request.getInspectionDate().plusMonths(6));

        return inspectionMapper.toResponse(inspectionRepository.save(inspection));
    }

    // ── Get all inspections for a vehicle ─────────────────

    public List<InspectionDto.Response> getByVehicleId(Long vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new NotFoundException("vehicle.not.found", vehicleId);
        }
        return inspectionRepository.findByVehicleId(vehicleId)
                .stream()
                .map(inspectionMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ── Get upcoming inspections ──────────────────────────
    // Returns vehicles whose inspection is due within 30 days

    public List<InspectionDto.Response> getUpcoming() {
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        return inspectionRepository.findByNextDueDateBefore(thirtyDaysFromNow)
                .stream()
                .map(inspectionMapper::toResponse)
                .collect(Collectors.toList());
    }
}