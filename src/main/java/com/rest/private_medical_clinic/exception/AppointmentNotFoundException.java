package com.rest.private_medical_clinic.exception;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(long id) {
        super("Appointment with id " + id + " not found");
    }
}