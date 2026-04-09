package com.edulink.attendanceservice.exception;

public class AttendanceAlreadyExistsException extends RuntimeException {
    public AttendanceAlreadyExistsException(String message) {
        super(message);
    }

    public AttendanceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
