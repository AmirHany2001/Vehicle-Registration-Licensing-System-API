package com.vehicleregistration.vehicle_registration_system.Controllers;

import com.vehicleregistration.vehicle_registration_system.DTOs.DashboardDto;
import com.vehicleregistration.vehicle_registration_system.Services.Application.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // GET /admin/dashboard — ADMIN only
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDto> getDashboard() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
}