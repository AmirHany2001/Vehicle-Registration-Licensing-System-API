package com.vehicleregistration.vehicle_registration_system.DTOs;



import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;


public class LicenseDto {

    @Data
    public static class Request {

        @NotNull(message = "{license.vehicleId.required}")
        private Long vehicleId;

        @NotNull(message = "{license.issueDate.required}")
        private LocalDate issueDate;

        // No expiryDate — calculated as issueDate + 1 year in service
    }

    @Data
    public static class Response {
        private Long id;
        private LocalDate issueDate;
        private LocalDate expiryDate;
        private boolean active;
        private Long vehicleId;
        private String vehiclePlateNumber;
        private boolean expired;
    }
}