package com.edulink.courseservice.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String courseCode) {
        super("Course not found with courseCode: " + courseCode);
    }
}
