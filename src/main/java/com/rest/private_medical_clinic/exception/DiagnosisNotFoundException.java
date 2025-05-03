package com.rest.private_medical_clinic.exception;

public class DiagnosisNotFoundException extends RuntimeException {

    public DiagnosisNotFoundException(long id) {
        super("Diagnosis with id " + id + " not found");
    }
}
