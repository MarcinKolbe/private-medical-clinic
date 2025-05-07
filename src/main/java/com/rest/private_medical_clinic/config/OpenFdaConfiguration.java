package com.rest.private_medical_clinic.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OpenFdaConfiguration {

    @Value("${openFda.apiEndpoint}")
    private String apiEndpoint;
}
