package com.vehicleregistration.vehicle_registration_system.Mapper;


import com.vehicleregistration.vehicle_registration_system.DTOs.VehicleDto;
import com.vehicleregistration.vehicle_registration_system.Entities.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    // Entity → Response DTO
    // Map nested owner fields to flat fields in response
    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "ownerName", source = "owner.fullName")
    VehicleDto.Response toResponse(Vehicle vehicle);

    // Request DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)   // always PENDING on creation
    @Mapping(target = "owner", ignore = true)     // set in service via ownerId
    @Mapping(target = "license", ignore = true)
    Vehicle toEntity(VehicleDto.Request request);
}