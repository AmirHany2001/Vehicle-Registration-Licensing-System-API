package com.vehicleregistration.vehicle_registration_system.Mapper;



import com.vehicleregistration.vehicle_registration_system.DTOs.LicenseDto;
import com.vehicleregistration.vehicle_registration_system.Entities.License;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LicenseMapper {

    // Entity → Response DTO
    @Mapping(target = "vehicleId", source = "vehicle.id")
    @Mapping(target = "vehiclePlateNumber", source = "vehicle.plateNumber")
    @Mapping(target = "expired", expression = "java(license.getExpiryDate().isBefore(java.time.LocalDate.now()))")
    LicenseDto.Response toResponse(License license);

    // Request DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "expiryDate", ignore = true)  // calculated in service
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "vehicle", ignore = true)      // set in service via vehicleId
    License toEntity(LicenseDto.Request request);
}