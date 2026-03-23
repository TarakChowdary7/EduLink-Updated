package com.edulink.identityservice.controller;

import com.edulink.identityservice.dto.*;
import com.edulink.identityservice.entity.Role;
import com.edulink.identityservice.exception.EduLinkException;
import com.edulink.identityservice.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
@PreAuthorize("hasRole('OPERATOR')")
public class OperatorController {

    private final UserManagementService userManagementService;

    public OperatorController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/create-compliance-officer")
    public ResponseEntity<ApiResponse<UserResponse>> createComplianceOfficer(
            Authentication auth, @Valid @RequestBody CreateUserRequest request) {
        request.setRole(Role.COMPLIANCE_OFFICER);
        UserResponse response = userManagementService.createUser(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Compliance Officer created successfully", response));
    }

    @PostMapping("/create-board-officer")
    public ResponseEntity<ApiResponse<UserResponse>> createBoardOfficer(
            Authentication auth, @Valid @RequestBody CreateUserRequest request) {
        request.setRole(Role.EDUCATION_BOARD_OFFICER);
        UserResponse response = userManagementService.createUser(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Education Board Officer created successfully", response));
    }

    @PostMapping("/create-regulator")
    public ResponseEntity<ApiResponse<UserResponse>> createRegulator(
            Authentication auth, @Valid @RequestBody CreateUserRequest request) {
        request.setRole(Role.REGULATOR);
        UserResponse response = userManagementService.createUser(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Regulator created successfully", response));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userManagementService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved", users));
    }
}
