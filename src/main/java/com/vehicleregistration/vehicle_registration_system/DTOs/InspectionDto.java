package com.vehicleregistration.vehicle_registration_system.DTOs;


import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;


public class InspectionDto {

    @Data
    public static class Request {

        @NotNull(message = "{inspection.vehicleId.required}")
        private Long vehicleId;

        @NotNull(message = "{inspection.date.required}")
        @PastOrPresent(message = "{inspection.date.future}")
        private LocalDate inspectionDate;

        @NotNull(message = "{inspection.passed.required}")
        private Boolean passed;

        @Size(max = 500, message = "{inspection.notes.size}")
        private String notes;

        // No nextDueDate — calculated as inspectionDate + 6 months in service
    }

    @Data
    public static class Response {
        private Long id;
        private LocalDate inspectionDate;
        private LocalDate nextDueDate;
        private boolean passed;
        private String notes;
        private Long vehicleId;
        private String vehiclePlateNumber;
        private boolean validForRenewal;
    }
}
