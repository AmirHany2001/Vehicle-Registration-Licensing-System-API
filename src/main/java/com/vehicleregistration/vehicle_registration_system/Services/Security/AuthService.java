package com.vehicleregistration.vehicle_registration_system.Services.Security;

import com.vehicleregistration.vehicle_registration_system.DTOs.AuthDto;
import com.vehicleregistration.vehicle_registration_system.DTOs.UserDto;
import com.vehicleregistration.vehicle_registration_system.Entities.User;
import com.vehicleregistration.vehicle_registration_system.Mapper.UserMapper;
import com.vehicleregistration.vehicle_registration_system.Models.DuplicateResourceException;
import com.vehicleregistration.vehicle_registration_system.Models.NotFoundException;
import com.vehicleregistration.vehicle_registration_system.Models.UnauthorizedException;
import com.vehicleregistration.vehicle_registration_system.Repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Value("${application.security.admin.secret}")
    private String adminSecret;

    public AuthDto.Response register(UserDto.Request request , String secret) {


        if(secret.isBlank()) {
            throw new UnauthorizedException("header.missed");
        }

        // 1. Check duplicate email
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("user.duplicate.email", request.getEmail());
        }

        // 2. Check duplicate national ID
        if (userRepository.existsByNationalId(request.getNationalId())) {
            throw new DuplicateResourceException("user.duplicate.nationalId", request.getNationalId());
        }



        // 3. Map request to entity
        User user = userMapper.toEntity(request);

        // 4. Hash password — NEVER save plain text password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 5. Set default role
        user.setRole(secret.equals(adminSecret) ? User.Role.ADMIN : User.Role.USER);

        User savedUser = userRepository.save(user);

        // 6. Generate token using email
        String token = jwtService.generateToken(savedUser.getEmail());

        // 7. return response
        return buildResponse(token, savedUser);
    }

    // ── Login ─────────────────────────────────────────────
    public AuthDto.Response login(AuthDto.Request request) {

        // 1. Validate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Load user
        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new NotFoundException("user.not.found.email", request.getEmail()));

        // 3. Generate token
        String token = jwtService.generateToken(user.getEmail());

        // 4. Return response
        return buildResponse(token, user);
    }

    // ── Build response ────────────────────────────────────
    private AuthDto.Response buildResponse(String token, User user) {
        AuthDto.Response response = new AuthDto.Response();
        response.setToken(token);
        response.setTokenType("Bearer");
        response.setUserId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }
}
