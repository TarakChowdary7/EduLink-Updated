package com.edulink.identityservice.service;

import com.edulink.identityservice.dto.*;
import com.edulink.identityservice.entity.User;
import com.edulink.identityservice.exception.EduLinkException;
import com.edulink.identityservice.repository.UserRepository;
import com.edulink.identityservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EduLinkException("Invalid email or password", HttpStatus.UNAUTHORIZED));

        if (!user.isActive()) {
            throw new EduLinkException("Account is deactivated", HttpStatus.FORBIDDEN);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new EduLinkException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        log.info("User logged in: {} with role: {}", user.getEmail(), user.getRole());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .role(user.getRole().name())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .userId(user.getId())
                .mustChangePassword(user.isMustChangePassword())
                .build();
    }

    public ApiResponse<String> changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EduLinkException("User not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new EduLinkException("Current password is incorrect", HttpStatus.BAD_REQUEST);
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new EduLinkException("New password and confirm password do not match", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setMustChangePassword(false);
        user.setTemporaryPassword(null);
        userRepository.save(user);

        return ApiResponse.success("Password changed successfully", null);
    }

    public LoginResponse refreshToken(String refreshToken) {
        try {
            String email = jwtUtil.extractEmail(refreshToken);
            if (jwtUtil.isTokenExpired(refreshToken)) {
                throw new EduLinkException("Refresh token expired", HttpStatus.UNAUTHORIZED);
            }
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new EduLinkException("User not found", HttpStatus.NOT_FOUND));

            String newAccessToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());
            String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());

            return LoginResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .tokenType("Bearer")
                    .role(user.getRole().name())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .userId(user.getId())
                    .mustChangePassword(user.isMustChangePassword())
                    .build();
        } catch (EduLinkException e) {
            throw e;
        } catch (Exception e) {
            throw new EduLinkException("Invalid refresh token", HttpStatus.UNAUTHORIZED);
        }
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EduLinkException("User not found", HttpStatus.NOT_FOUND));
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .active(user.isActive())
                .mustChangePassword(user.isMustChangePassword())
                .temporaryPassword(user.isMustChangePassword() ? user.getTemporaryPassword() : null)
                .schoolId(user.getSchoolId())
                .classId(user.getClassId())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
