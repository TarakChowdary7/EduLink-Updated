package com.edulink.courseservice.controller;

import com.edulink.courseservice.entity.Course;
import com.edulink.courseservice.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class InternalController {
    private final CourseService courseService;

    public InternalController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/course/by-code/{courseCode}")
    public ResponseEntity<Course> getCourseByCode(@PathVariable String courseCode) {
        Course course = courseService.getCourseByCode(courseCode);
        if (course != null) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }
}