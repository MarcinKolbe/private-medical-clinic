package com.rest.private_medical_clinic.client;

import com.rest.private_medical_clinic.config.OpenFdaConfiguration;
import com.rest.private_medical_clinic.domain.dto.OpenFdaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class OpenFdaClient {

    private final RestTemplate restTemplate;
    private final OpenFdaConfiguration config;

    public OpenFdaResponseDto.DrugLabelDto fetchDrugLabel(String drugName) {
        String url = UriComponentsBuilder
                .fromHttpUrl(config.getApiEndpoint())
                .queryParam("search", "openfda.generic_name:"+ drugName)
                .queryParam("limit", 1)
                .toUriString();

        OpenFdaResponseDto resp = restTemplate.getForObject(url, OpenFdaResponseDto.class);
        if (resp != null && !resp.getResults().isEmpty()) {
            return resp.getResults().get(0);
        }
        throw new IllegalStateException("Brak danych o leku: " + drugName);
    }
}
