package com.edulink.attendanceservice.controller;
import com.edulink.attendanceservice.dto.ApiResponse;
import com.edulink.attendanceservice.entity.Attendance;
import com.edulink.attendanceservice.exception.AttendanceAlreadyExistsException;
import com.edulink.attendanceservice.exception.StudentNotFoundException;
import com.edulink.attendanceservice.exception.CourseNotFoundException;
import com.edulink.attendanceservice.repository.AttendanceRepository;
import com.edulink.attendanceservice.util.JwtExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AttendanceController {
    private final AttendanceRepository attendanceRepo;
    private final JwtExtractor jwtExtractor;

    public AttendanceController(AttendanceRepository attendanceRepo, JwtExtractor jwtExtractor) {
        this.attendanceRepo = attendanceRepo;
        this.jwtExtractor = jwtExtractor;
    }

    @PostMapping("/teacher/mark-attendance")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponse<Attendance>> markAttendance(HttpServletRequest req, @Valid @RequestBody Attendance attendance) {
        String teacherEmail = jwtExtractor.extractEmail(req);
        attendance.setMarkedBy(teacherEmail);

        // Check if attendance already exists for this student, course, and date
        Optional<Attendance> existingAttendance = attendanceRepo.findByStudentIdAndCourseIdAndAttendanceDate(
                attendance.getStudentId(), attendance.getCourseId(), attendance.getAttendanceDate());

        if (existingAttendance.isPresent()) {
            throw new AttendanceAlreadyExistsException(
                "Attendance already marked for student " + attendance.getStudentId() +
                " in course " + attendance.getCourseId() + " on date " + attendance.getAttendanceDate());
        }



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
