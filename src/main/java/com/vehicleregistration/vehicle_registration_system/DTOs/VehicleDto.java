package com.vehicleregistration.vehicle_registration_system.DTOs;



import com.vehicleregistration.vehicle_registration_system.Entities.Vehicle.VehicleStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
;

public class VehicleDto {

    @Data
    public static class Request {

        @NotBlank(message = "{vehicle.plateNumber.required}")
        @Pattern(
                regexp = "^[A-Z]{1,3}[0-9]{1,4}[A-Z]{1,3}$",
                message = "{vehicle.plateNumber.invalid}"
        )
        private String plateNumber;

        @NotBlank(message = "{vehicle.brand.required}")
        private String brand;

        @NotBlank(message = "{vehicle.model.required}")
        private String model;

        @NotNull(message = "{vehicle.year.required}")
        @Min(value = 1990, message = "{vehicle.year.min}")
        @Max(value = 2025, message = "{vehicle.year.max}")
        private Integer year;

        @NotBlank(message = "{vehicle.color.required}")
        private String color;

        @NotNull(message = "{vehicle.ownerId.required}")
        private Long ownerId;
    }

    @Data
    public static class Response {
        private Long id;
        private String plateNumber;
        private String brand;
        private String model;
        private Integer year;
        private String color;
        private VehicleStatus status;
        private Long ownerId;
        private String ownerName;
    }
}