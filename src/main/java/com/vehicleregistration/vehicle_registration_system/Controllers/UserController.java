package com.vehicleregistration.vehicle_registration_system.Controllers;


import com.vehicleregistration.vehicle_registration_system.DTOs.UserDto;
import com.vehicleregistration.vehicle_registration_system.Services.Application.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    // GET /users
    @GetMapping
    public ResponseEntity<List<UserDto.Response>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    // PUT /users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserDto.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDto.Request request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
