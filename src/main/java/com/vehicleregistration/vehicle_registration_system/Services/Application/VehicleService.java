package com.vehicleregistration.vehicle_registration_system.Services.Application;


import com.vehicleregistration.vehicle_registration_system.Models.BusinessLogicException;
import com.vehicleregistration.vehicle_registration_system.Models.DuplicateResourceException;
import com.vehicleregistration.vehicle_registration_system.Models.NotFoundException;
import com.vehicleregistration.vehicle_registration_system.DTOs.VehicleDto;
import com.vehicleregistration.vehicle_registration_system.Mapper.VehicleMapper;
import com.vehicleregistration.vehicle_registration_system.Entities.User;
import com.vehicleregistration.vehicle_registration_system.Entities.Vehicle;
import com.vehicleregistration.vehicle_registration_system.Models.UnauthorizedException;
import com.vehicleregistration.vehicle_registration_system.Repos.UserRepository;
import com.vehicleregistration.vehicle_registration_system.Repos.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;

    @Value("${application.security.admin.secret}")
    private String adminSecret;

    // ── Register new vehicle ──────────────────────────────

    public VehicleDto.Response register(VehicleDto.Request request) {

        // 1. Check duplicate plate number
        if (vehicleRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new DuplicateResourceException("vehicle.duplicate.plate",
                    request.getPlateNumber());
        }

        // 2. Find owner — must exist
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new NotFoundException("user.not.found",
                        request.getOwnerId()));

        // 3. Map request to entity
        Vehicle vehicle = vehicleMapper.toEntity(request);

        // 4. Set owner and default status
        vehicle.setOwner(owner);
        vehicle.setStatus(Vehicle.VehicleStatus.PENDING);

        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    // ── Get vehicle by id ─────────────────────────────────

    public VehicleDto.Response getById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("vehicle.not.found", id));
        return vehicleMapper.toResponse(vehicle);
    }

    // ── Get all vehicles by owner ─────────────────────────

    public List<VehicleDto.Response> getByOwner(Long ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("user.not.found", ownerId);
        }
        return vehicleRepository.findByOwnerId(ownerId)
                .stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ── Admin approve vehicle ─────────────────────────────

    public VehicleDto.Response approve(Long id , String secret) {

        if(!secret.equals(adminSecret)){
            throw new UnauthorizedException("auth.access.denied");
        }

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("vehicle.not.found", id));

        // Can only approve PENDING vehicles
        if (vehicle.getStatus() != Vehicle.VehicleStatus.PENDING) {
            throw new BusinessLogicException("vehicle.already.approved");
        }

        vehicle.setStatus(Vehicle.VehicleStatus.APPROVED);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    // ── Admin reject vehicle ──────────────────────────────

    public VehicleDto.Response reject(Long id , String secret) {

        if(!secret.equals(adminSecret)){
            throw new UnauthorizedException("auth.access.denied");
        }

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("vehicle.not.found", id));

        if (vehicle.getStatus() != Vehicle.VehicleStatus.PENDING) {
            throw new BusinessLogicException("vehicle.already.rejected");
        }

        vehicle.setStatus(Vehicle.VehicleStatus.REJECTED);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }
}