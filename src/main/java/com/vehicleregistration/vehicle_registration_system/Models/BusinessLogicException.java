package com.vehicleregistration.vehicle_registration_system.Models;

// 400 — business rule violated
// Usage: inspection invalid, vehicle not approved, fine already paid
import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {

    private final String code;
    private final Object[] args;

    public BusinessLogicException(String code) {
        super(code);
        this.code = code;
        this.args = null;
    }

    public BusinessLogicException(String code, Object... args) {
        super(code);
        this.code = code;
        this.args = args;
    }
}