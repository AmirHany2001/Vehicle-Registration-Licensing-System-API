package com.vehicleregistration.vehicle_registration_system.Mapper;



import com.vehicleregistration.vehicle_registration_system.DTOs.InspectionDto;
import com.vehicleregistration.vehicle_registration_system.Entities.Inspection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InspectionMapper {

    // Entity → Response DTO
    @Mapping(target = "vehicleId", source = "vehicle.id")
    @Mapping(target = "vehiclePlateNumber", source = "vehicle.plateNumber")
    // Valid for renewal if passed AND nextDueDate is after today
    @Mapping(target = "validForRenewal", expression = "java(inspection.isPassed() && inspection.getNextDueDate().isAfter(java.time.LocalDate.now()))")
    InspectionDto.Response toResponse(Inspection inspection);

    // Request DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nextDueDate", ignore = true)  // calculated in service
    @Mapping(target = "vehicle", ignore = true)       // set in service via vehicleId
    Inspection toEntity(InspectionDto.Request request);
}