package com.vena_project.crowd_funding.exception;

public class AccessDeniedForAdminException extends RuntimeException {
    public AccessDeniedForAdminException(String message) {
        super(message);
    }
}

