package com.vehicleregistration.vehicle_registration_system.Models;


// 409 — unique constraint violated
// Usage: duplicate plate number, duplicate email, duplicate national ID

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {

    private final String code;
    private final Object[] args;

    public DuplicateResourceException(String code) {
        super(code);
        this.code = code;
        this.args = null;
    }

    public DuplicateResourceException(String code, Object... args) {
        super(code);
        this.code = code;
        this.args = args;
    }
}