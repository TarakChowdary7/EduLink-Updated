package com.edulink.courseservice.controller;
import com.edulink.courseservice.dto.ApiResponse;
import com.edulink.courseservice.entity.LearningMaterial;
import com.edulink.courseservice.entity.Assignment;
import com.edulink.courseservice.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/student") @PreAuthorize("hasRole('STUDENT')")
public class StudentController {
    private final CourseService courseService;

    public StudentController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/materials/{courseCode}")
    public ResponseEntity<ApiResponse<List<LearningMaterial>>> getMaterials(@PathVariable String courseCode) {
        List<LearningMaterial> materials = courseService.getMaterialsByCourseCode(courseCode);
        return ResponseEntity.ok(ApiResponse.success("Materials retrieved", materials));
    }

    @GetMapping("/assignments/{courseCode}")
    public ResponseEntity<ApiResponse<List<Assignment>>> getAssignments(@PathVariable String courseCode) {
        List<Assignment> assignments = courseService.getAssignmentsByCourseCode(courseCode);
        return ResponseEntity.ok(ApiResponse.success("Assignments retrieved", assignments));
    }
}