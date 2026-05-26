package com.vehicleregistration.vehicle_registration_system.Controllers;


import com.vehicleregistration.vehicle_registration_system.DTOs.InspectionDto;
import com.vehicleregistration.vehicle_registration_system.Services.Application.InspectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inspections")
@RequiredArgsConstructor
public class InspectionController {

    private final InspectionService inspectionService;

    // POST /inspections
    @PostMapping
    public ResponseEntity<InspectionDto.Response> addInspection(@Valid @RequestBody InspectionDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inspectionService.addInspection(request));
    }

    // GET /inspections/vehicle/{vehicleId}
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<InspectionDto.Response>> getByVehicleId(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(inspectionService.getByVehicleId(vehicleId));
    }

    // GET /inspections/upcoming
    @GetMapping("/upcoming")
    public ResponseEntity<List<InspectionDto.Response>> getUpcoming() {
        return ResponseEntity.ok(inspectionService.getUpcoming());
    }
}
