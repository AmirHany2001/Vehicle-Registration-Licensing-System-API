package com.vehicleregistration.vehicle_registration_system.Config.Security;


import com.vehicleregistration.vehicle_registration_system.Services.Security.JwtService;
import com.vehicleregistration.vehicle_registration_system.Services.Security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get token from header
        String authHeader = request.getHeader("Authorization");

        // 2. No token → skip
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 3. Extract token and email
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        // 4. If email found and not yet authenticated
        if (email != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            // 5. Load user from DB
            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(email);

            // 6. Validate token
            if (jwtService.isTokenValid(token, email)) {

                // 7. Set authentication
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        // 8. Continue
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return request.getRequestURI().contains("login") || request.getRequestURI().contains("register");
    }
}
