package com.vehicleregistration.vehicle_registration_system.DTOs;


import com.vehicleregistration.vehicle_registration_system.Entities.User.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

public class AuthDto {

    @Data
    public static class Request {

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    public static class Response {

        private String token;
        private String tokenType = "Bearer";
        private Long userId;
        private String fullName;
        private String email;
        private Role role;
    }
}
