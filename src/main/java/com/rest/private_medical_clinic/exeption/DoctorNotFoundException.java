package com.rest.private_medical_clinic.exeption;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(long id) {
        super("Doctor with id " + id + " not found");
    }
}
