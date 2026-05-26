package com.vehicleregistration.vehicle_registration_system.Controllers;


import com.vehicleregistration.vehicle_registration_system.DTOs.VehicleDto;
import com.vehicleregistration.vehicle_registration_system.Services.Application.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    // POST /vehicles/register
    @PostMapping("/register")
    public ResponseEntity<VehicleDto.Response> register(@Valid @RequestBody VehicleDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.register(request));
    }

    // GET /vehicles/{id}
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto.Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    // GET /vehicles/owner/{ownerId}
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<VehicleDto.Response>> getByOwner(
            @PathVariable Long ownerId) {
        return ResponseEntity.ok(vehicleService.getByOwner(ownerId));
    }

    // PUT /vehicles/{id}/approve — admin only
    @PutMapping("/{id}/approve")
    public ResponseEntity<VehicleDto.Response> approve(
            @PathVariable Long id ,
            @RequestHeader("secret") String secret) {
        return ResponseEntity.ok(vehicleService.approve(id , secret));
    }

    // PUT /vehicles/{id}/reject — admin only
    @PutMapping("/{id}/reject")
    public ResponseEntity<VehicleDto.Response> reject(
            @PathVariable Long id,
            @RequestHeader("secret") String secret) {
        return ResponseEntity.ok(vehicleService.reject(id ,secret));
    }
}
