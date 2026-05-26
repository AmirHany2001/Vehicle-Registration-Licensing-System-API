package com.vehicleregistration.vehicle_registration_system.Services.Application;

import com.vehicleregistration.vehicle_registration_system.Models.DuplicateResourceException;
import com.vehicleregistration.vehicle_registration_system.Models.NotFoundException;
import com.vehicleregistration.vehicle_registration_system.DTOs.UserDto;
import com.vehicleregistration.vehicle_registration_system.Mapper.UserMapper;
import com.vehicleregistration.vehicle_registration_system.Entities.User;
import com.vehicleregistration.vehicle_registration_system.Repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    // ── Get user by id ────────────────────────────────────

    public UserDto.Response getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user.not.found", id));
        return userMapper.toResponse(user);
    }

    // ── Get all users ─────────────────────────────────────

    public List<UserDto.Response> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ── Update user ───────────────────────────────────────

    public UserDto.Response update(Long id, UserDto.Request request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user.not.found", id));


        // Only update if email changed and new email not taken
        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) &&
                userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("user.duplicate.email", request.getEmail());
        }


        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        // Only re-hash if password changed
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        /* If the fields are exactly the same, Hibernate completely skips the SQL UPDATE statement.
         It won't hit your database write-log at all! */
        return userMapper.toResponse(userRepository.save(user));
    }

    // ── Delete user ───────────────────────────────────────

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("user.not.found", id);
        }
        userRepository.deleteById(id);
    }

}