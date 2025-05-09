package com.rest.private_medical_clinic.observer;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DoctorScheduleTemplateChangedEvent extends ApplicationEvent {

    private final long templateId;

    public DoctorScheduleTemplateChangedEvent(Object source, long templateId) {
        super(source);
        this.templateId = templateId;
    }
}
