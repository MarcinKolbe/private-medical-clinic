package com.rest.private_medical_clinic.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CalendarificConfiguration {

    @Value("${calendarific.apiKey}")
    private String apiKey;
    @Value("${calendarific.apiEndpoint}")
    private String apiEndpoint;
}
