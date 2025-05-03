package com.rest.private_medical_clinic.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(long id) {
        super("Patient with id " + id + " not found");
    }
}
