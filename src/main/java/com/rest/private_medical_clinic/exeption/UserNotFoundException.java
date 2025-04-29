package com.rest.private_medical_clinic.exeption;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super("User with id " + id + " not found");
    }
}
