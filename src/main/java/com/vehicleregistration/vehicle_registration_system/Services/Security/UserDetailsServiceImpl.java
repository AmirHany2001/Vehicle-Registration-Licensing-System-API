package com.vehicleregistration.vehicle_registration_system.Services.Security;


import com.vehicleregistration.vehicle_registration_system.Models.NotFoundException;
import com.vehicleregistration.vehicle_registration_system.Repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final String ROLE = "ROLE_";
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws NotFoundException {

        return userRepository.findByEmailIgnoreCase(email)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority(
                                ROLE + user.getRole().name()))
                ))
                .orElseThrow(() -> new NotFoundException(
                        "user.not.found.email" + email));
    }
}
