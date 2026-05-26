package com.vehicleregistration.vehicle_registration_system.Controllers;


import com.vehicleregistration.vehicle_registration_system.DTOs.LicenseDto;
import com.vehicleregistration.vehicle_registration_system.Services.Application.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    // POST /licenses/renew/{vehicleId}
    @PostMapping("/renew/{vehicleId}")
    public ResponseEntity<LicenseDto.Response> renew(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(licenseService.renew(vehicleId));
    }

    // GET /licenses/{vehicleId}
    @GetMapping("/{vehicleId}")
    public ResponseEntity<LicenseDto.Response> getByVehicleId(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(licenseService.getByVehicleId(vehicleId));
    }

    // GET /licenses/expired
    @GetMapping("/expired")
    public ResponseEntity<List<LicenseDto.Response>> getExpired() {
        return ResponseEntity.ok(licenseService.getExpired());
    }
}
