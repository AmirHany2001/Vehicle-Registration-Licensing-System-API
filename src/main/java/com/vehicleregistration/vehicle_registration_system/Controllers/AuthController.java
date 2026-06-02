package com.vehicleregistration.vehicle_registration_system.Controllers;


import com.vehicleregistration.vehicle_registration_system.DTOs.AuthDto;
import com.vehicleregistration.vehicle_registration_system.DTOs.UserDto;
import com.vehicleregistration.vehicle_registration_system.Services.Security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthDto.Response> register(
            @Valid @RequestBody UserDto.Request request ,
            @RequestHeader(
                    value = "secret",
                    required = false,
                    defaultValue = "") String secret) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(request , secret));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto.Response> login(@Valid @RequestBody AuthDto.Request request) {
        return ResponseEntity.ok(authService.login(request));
    }


}
