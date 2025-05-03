package com.rest.private_medical_clinic.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(long id) {
        super("Review with id " + id + " not found");
    }
}
