package com.edulink.courseservice.exception;

public class SchoolNotFoundException extends RuntimeException {
    public SchoolNotFoundException(String schoolId) {
        super("School not found with schoolId: " + schoolId);
    }
}
