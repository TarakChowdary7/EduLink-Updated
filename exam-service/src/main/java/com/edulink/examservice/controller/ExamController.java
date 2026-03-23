package com.edulink.examservice.controller;
import com.edulink.examservice.dto.ApiResponse;
import com.edulink.examservice.entity.*;
import com.edulink.examservice.service.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ExamController {
    private final ExamService examService;
    private final GradeService gradeService;

    public ExamController(ExamService examService, GradeService gradeService) {
        this.examService = examService;
        this.gradeService = gradeService;
    }

    @PostMapping("/teacher/create-exam")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponse<Exam>> createExam(@RequestBody Exam exam) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Exam created", examService.createExam(exam)));
    }

    @PostMapping("/teacher/grade-student")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponse<Grade>> gradeStudent(@RequestBody Grade grade) {
        return ResponseEntity.ok(ApiResponse.success("Student graded", gradeService.gradeStudent(grade)));
    }

    @GetMapping("/student/grades")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<List<Grade>>> getStudentGrades(@RequestParam(required = false) Long studentId) {
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
        return ResponseEntity.ok(ApiResponse.success("Grades retrieved", gradeService.getGradesByStudentId(studentId)));
    }
}
