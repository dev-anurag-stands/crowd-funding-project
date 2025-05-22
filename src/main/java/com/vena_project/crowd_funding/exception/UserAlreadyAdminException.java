package com.vena_project.crowd_funding.exception;

public class UserAlreadyAdminException extends RuntimeException{
    public UserAlreadyAdminException(String message){
        super(message);
    }
}
