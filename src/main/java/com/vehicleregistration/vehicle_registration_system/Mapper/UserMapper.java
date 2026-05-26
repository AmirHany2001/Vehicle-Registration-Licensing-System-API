package com.vehicleregistration.vehicle_registration_system.Mapper;


import com.vehicleregistration.vehicle_registration_system.DTOs.UserDto;
import com.vehicleregistration.vehicle_registration_system.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity → Response DTO
    UserDto.Response toResponse(User user);

    // Request DTO → Entity
    // Ignore fields that are set by the system, not the client
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    User toEntity(UserDto.Request request);
}
