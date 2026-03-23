package com.edulink.identityservice.controller;

import com.edulink.identityservice.dto.*;
import com.edulink.identityservice.entity.Role;
import com.edulink.identityservice.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminController {

    private final UserManagementService userManagementService;

    public AdminController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/create-teacher")
    public ResponseEntity<ApiResponse<UserResponse>> createTeacher(
            Authentication auth, @Valid @RequestBody CreateUserRequest request) {
        request.setRole(Role.TEACHER);
        UserResponse response = userManagementService.createUser(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Teacher account created. Temporary password: " + response.getTemporaryPassword(), response));
    }

    @PostMapping("/create-student")
    public ResponseEntity<ApiResponse<UserResponse>> createStudent(
            Authentication auth, @Valid @RequestBody CreateUserRequest request) {
        request.setRole(Role.STUDENT);
        UserResponse response = userManagementService.createUser(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student account created. Temporary password: " + response.getTemporaryPassword(), response));
    }

    @GetMapping("/teachers")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getTeachers() {
        return ResponseEntity.ok(ApiResponse.success("Teachers retrieved",
                userManagementService.getUsersByRole(Role.TEACHER)));
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getStudents() {
        return ResponseEntity.ok(ApiResponse.success("Students retrieved",
                userManagementService.getUsersByRole(Role.STUDENT)));
    }
}
