package com.vehicleregistration.vehicle_registration_system.DTOs;



import com.vehicleregistration.vehicle_registration_system.Entities.User.Role;
import jakarta.validation.constraints.*;
import lombok.Data;


public class UserDto {

    @Data
    public static class Request {

        @NotBlank(message = "{user.fullName.required}")
        @Size(min = 3, max = 100, message = "{user.fullName.size}")
        private String fullName;

        @NotBlank(message = "{user.email.required}")
        @Email(message = "{user.email.invalid}")
        private String email;

        @NotBlank(message = "{user.password.required}")
        @Size(min = 8, message = "{user.password.size}")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
                message = "{user.password.pattern}"
        )
        private String password;

        @NotBlank(message = "{user.phone.required}")
        @Pattern(regexp = "^01[0-9]{9}$", message = "{user.phone.invalid}")
        private String phoneNumber;

        @NotBlank(message = "{user.nationalId.required}")
        @Size(min = 14, max = 14, message = "{user.nationalId.size}")
        @Pattern(regexp = "^[0-9]{14}$", message = "{user.nationalId.pattern}")
        private String nationalId;
    }

    @Data
    public static class Response {
        private Long id;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String nationalId;
        private Role role;
        // No password — never send it back to the client
    }
}