package com.vehicleregistration.vehicle_registration_system.Models;


// 403 — access not allowed
// Usage: user accessing another user's vehicle, non-admin hitting admin endpoint

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final String code;
    private final Object[] args;

    public UnauthorizedException(String code) {
        super(code);
        this.code = code;
        this.args = null;
    }

    public UnauthorizedException(String code, Object... args) {
        super(code);
        this.code = code;
        this.args = args;
    }
}
