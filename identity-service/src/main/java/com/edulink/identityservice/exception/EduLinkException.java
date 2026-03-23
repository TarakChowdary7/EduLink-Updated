package com.edulink.identityservice.exception;

import org.springframework.http.HttpStatus;

public class EduLinkException extends RuntimeException {
    private final HttpStatus status;

    public EduLinkException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() { return status; }
}
