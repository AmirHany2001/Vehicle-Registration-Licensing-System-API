package com.vehicleregistration.vehicle_registration_system.DTOs;


import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;


public class FineDto {

    @Data
    public static class Request {

        @NotNull(message = "{fine.vehicleId.required}")
        private Long vehicleId;

        @NotNull(message = "{fine.amount.required}")
        @DecimalMin(value = "0.01", message = "{fine.amount.min}")
        @Digits(integer = 8, fraction = 2, message = "{fine.amount.digits}")
        private BigDecimal amount;

        @NotBlank(message = "{fine.reason.required}")
        @Size(max = 255, message = "{fine.reason.size}")
        private String reason;

        // No issuedDate — always set to today in service
        // No paid — fines always created as unpaid
    }

    @Data
    public static class Response {
        private Long id;
        private BigDecimal amount;
        private String reason;
        private LocalDate issuedDate;
        private boolean paid;
        private Long vehicleId;
        private String vehiclePlateNumber;
        private String ownerName;
    }
}