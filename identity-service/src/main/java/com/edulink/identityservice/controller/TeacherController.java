package com.edulink.identityservice.controller;

import com.edulink.identityservice.dto.ApiResponse;
import com.edulink.identityservice.dto.UserResponse;
import com.edulink.identityservice.entity.Role;
import com.edulink.identityservice.repository.StudentRepository;
import com.edulink.identityservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teacher")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public TeacherController(UserRepository userRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students-by-class")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getStudentsByClass(@RequestParam Long classId, @RequestParam String schoolId) {
        List<UserResponse> students = studentRepository.findByClassIdAndSchoolId(classId, schoolId)
                .stream()
                .map(student -> UserResponse.builder()
                        .id(student.getUserId())
                        .email(student.getEmail())
                        .fullName(student.getFullName())
                        .role(Role.STUDENT)
                        .active(true)
                        .schoolId(student.getSchoolId())
                        .classId(student.getClassId())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Students retrieved for class " + classId + " in school " + schoolId, students));
    }
}