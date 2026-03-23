package com.edulink.attendanceservice.controller;
import com.edulink.attendanceservice.dto.ApiResponse;
import com.edulink.attendanceservice.entity.Attendance;
import com.edulink.attendanceservice.repository.AttendanceRepository;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AttendanceController {
    private final AttendanceRepository attendanceRepo;

    public AttendanceController(AttendanceRepository attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    @PostMapping("/teacher/mark-attendance")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponse<Attendance>> markAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Attendance marked", attendanceRepo.save(attendance)));
    }

    @GetMapping("/student/attendance")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<List<Attendance>>> getStudentAttendance(@RequestParam(required = false) Long studentId) {
        if (studentId == null) {
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof Long) {
                studentId = (Long) details;
            } else if (details instanceof String) {
                studentId = Long.parseLong((String) details);
            } else {
                throw new RuntimeException("Invalid student identity in token");
            }
        }
        return ResponseEntity.ok(ApiResponse.success("Attendance retrieved", attendanceRepo.findByStudentId(studentId)));
    }

    @GetMapping("/admin/attendance-report")
    @PreAuthorize("hasRole('SCHOOL_ADMIN') or hasRole('TEACHER') or hasRole('EDUCATION_BOARD_OFFICER')")
    public ResponseEntity<ApiResponse<List<Attendance>>> getAttendanceReport(@RequestParam String schoolId) {
        return ResponseEntity.ok(ApiResponse.success("Attendance report", attendanceRepo.findBySchoolId(schoolId)));
    }
}
