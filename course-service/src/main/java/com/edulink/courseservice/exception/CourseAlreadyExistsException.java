package com.edulink.courseservice.exception;

public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException(String courseCode) {
        super("Course already exists with courseCode: " + courseCode);
    }
}
