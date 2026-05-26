package com.vehicleregistration.vehicle_registration_system.Controllers;



import com.vehicleregistration.vehicle_registration_system.DTOs.FineDto;
import com.vehicleregistration.vehicle_registration_system.Services.Application.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fines")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    // GET /fines/owner/{ownerId}
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<FineDto.Response>> getByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(fineService.getByOwner(ownerId));
    }

    // GET /fines/vehicle/{vehicleId}
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<FineDto.Response>> getByVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(fineService.getByVehicle(vehicleId));
    }

    // POST /fines/pay/{fineId}
    @PostMapping("/pay/{fineId}")
    public ResponseEntity<FineDto.Response> pay(@PathVariable Long fineId) {
        return ResponseEntity.ok(fineService.pay(fineId));
    }
}
