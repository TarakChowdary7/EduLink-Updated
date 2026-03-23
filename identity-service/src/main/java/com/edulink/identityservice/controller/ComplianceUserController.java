package com.edulink.identityservice.controller;

import com.edulink.identityservice.dto.*;
import com.edulink.identityservice.entity.Role;
import com.edulink.identityservice.entity.School;
import com.edulink.identityservice.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance/identity")
@PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
public class ComplianceUserController {

    private final UserManagementService userManagementService;

    public ComplianceUserController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/create-school-admin")
    public ResponseEntity<ApiResponse<UserResponse>> createSchoolAdmin(
            Authentication auth, @Valid @RequestBody CreateUserRequest request) {
        request.setRole(Role.SCHOOL_ADMIN);
        UserResponse response = userManagementService.createUser(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("School Admin created successfully", response));
    }

    @PostMapping("/create-school")
    public ResponseEntity<ApiResponse<School>> createSchool(
            Authentication auth, @Valid @RequestBody SchoolCreateRequest request) {
        School school = userManagementService.createSchool(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("School created successfully", school));
    }

    @GetMapping("/schools/{schoolId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<School>> getSchoolById(@PathVariable String schoolId) {
        School school = userManagementService.getSchoolById(schoolId);
        return ResponseEntity.ok(ApiResponse.success("School retrieved successfully", school));
    }
}
