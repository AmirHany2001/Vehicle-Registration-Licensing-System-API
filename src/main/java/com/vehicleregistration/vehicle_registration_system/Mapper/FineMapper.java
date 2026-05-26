package com.vehicleregistration.vehicle_registration_system.Mapper;




import com.vehicleregistration.vehicle_registration_system.DTOs.FineDto;
import com.vehicleregistration.vehicle_registration_system.Entities.Fine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FineMapper {

    // Entity → Response DTO
    @Mapping(target = "vehicleId", source = "vehicle.id")
    @Mapping(target = "vehiclePlateNumber", source = "vehicle.plateNumber")
    @Mapping(target = "ownerName", source = "vehicle.owner.fullName")
    FineDto.Response toResponse(Fine fine);

    // Request DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "issuedDate", ignore = true)  // always set to today in service
    @Mapping(target = "paid", ignore = true)         // always false on creation
    @Mapping(target = "vehicle", ignore = true)      // set in service via vehicleId
    Fine toEntity(FineDto.Request request);
}
