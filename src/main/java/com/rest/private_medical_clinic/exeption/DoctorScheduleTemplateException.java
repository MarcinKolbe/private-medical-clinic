package com.rest.private_medical_clinic.exeption;

public class DoctorScheduleTemplateException extends RuntimeException {

    public DoctorScheduleTemplateException(long id) {
        super("Doctor Schedule with id " + id + " not found");
    }
}
