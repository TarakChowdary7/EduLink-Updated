package com.edulink.courseservice.controller;
import com.edulink.courseservice.dto.ApiResponse;
import com.edulink.courseservice.entity.*;
import com.edulink.courseservice.service.CourseService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/admin") @PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminCourseController {
    private final CourseService courseService;

    public AdminCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create-course")
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Course created", courseService.createCourse(course)));
    }

    @PostMapping("/create-class")
    public ResponseEntity<ApiResponse<ClassRoom>> createClass(@RequestBody ClassRoom c) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Class created", courseService.createClass(c)));
    }

    @GetMapping("/attendance-report")
    public ResponseEntity<ApiResponse<Object>> attendanceReport() {
        return ResponseEntity.ok(ApiResponse.success("Attendance report (see attendance-service)", null));
    }
}
