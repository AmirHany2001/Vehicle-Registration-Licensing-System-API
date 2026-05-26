package com.vehicleregistration.vehicle_registration_system.Models;


// 404 — entity not found in database
// Usage: vehicle not found, user not found, license not found

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code;
    private final Object[] args;

    // No args — throw new NotFoundException("vehicle.not.found")
    public NotFoundException(String code) {
        super(code);
        this.code = code;
        this.args = null;
    }

    // With args — throw new NotFoundException("vehicle.not.found", vehicleId)
    public NotFoundException(String code, Object... args) {
        super(code);
        this.code = code;
        this.args = args;
    }
}