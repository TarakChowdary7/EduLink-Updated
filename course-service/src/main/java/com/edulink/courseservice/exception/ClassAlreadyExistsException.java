package com.edulink.courseservice.exception;

public class ClassAlreadyExistsException extends RuntimeException {
    public ClassAlreadyExistsException(String className) {
        super("Class already exists with name: " + className);
    }
}
