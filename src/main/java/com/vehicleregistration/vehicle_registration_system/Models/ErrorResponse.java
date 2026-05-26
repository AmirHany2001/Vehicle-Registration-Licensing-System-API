package com.vehicleregistration.vehicle_registration_system.Models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;

    // Used for validation errors — holds field → error message pairs
    // Example: { "email": "Invalid email format", "phoneNumber": "Invalid phone" }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    // Constructor for single message errors (not validation)

}