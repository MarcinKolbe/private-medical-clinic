package com.rest.private_medical_clinic.exception;

public class DoctorAvailabilityException extends RuntimeException {

    public DoctorAvailabilityException(long id) {
        super("Doctor with id " + id + " has already been available.");
    }
}
