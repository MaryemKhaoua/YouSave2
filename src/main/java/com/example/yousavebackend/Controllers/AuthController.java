package com.example.yousavebackend.Controllers;

import com.example.yousavebackend.DTOs.AuthRequestDTO;
import com.example.yousavebackend.DTOs.JwtResponseDTO;
import com.example.yousavebackend.DTOs.RegisterRequestDTO;
import com.example.yousavebackend.DTOs.User.UserResponseDTO;
import com.example.yousavebackend.security.JwtService;
import com.example.yousavebackend.entities.User;
import com.example.yousavebackend.entities.Role;
import com.example.yousavebackend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    @Qualifier("userServiceImpl")
    IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponseDTO> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(),
                            authRequestDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                User user = userService.findByEmail(authRequestDTO.getEmail())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                String role = user.getRoles().stream()
                        .findFirst()
                        .map(Role::getName)
                        .orElseThrow(() -> new RuntimeException("User has no roles assigned"));

                String token = jwtService.GenerateToken(authRequestDTO.getEmail(), role);

                JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                        .accessToken(token)
                        .role(role)
                        .build();

                return ResponseEntity.ok(jwtResponseDTO);
            } else {
                throw new UsernameNotFoundException("Invalid user request..!!");
            }
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", authRequestDTO.getEmail(), e);
            return ResponseEntity.status(403).body(null);
        }
    }
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        try {
            UserResponseDTO userResponseDTO = userService.register(registerRequestDTO);
            return ResponseEntity.ok(userResponseDTO);
        } catch (Exception e) {
            logger.error("Registration failed for user: {}", registerRequestDTO.getEmail(), e);
            return ResponseEntity.badRequest().body(null);
        }
    }
}