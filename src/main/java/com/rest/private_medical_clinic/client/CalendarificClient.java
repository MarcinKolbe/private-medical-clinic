package com.rest.private_medical_clinic.client;

import com.rest.private_medical_clinic.config.CalendarificConfiguration;
import com.rest.private_medical_clinic.domain.dto.CalendarificResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CalendarificClient {

    private final RestTemplate restTemplate;
    private final CalendarificConfiguration calendarificConfiguration;

    public CalendarificResponseDto fetchHolidays(int year, String country) {

        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(calendarificConfiguration.getApiEndpoint())
                .queryParam("api_key", calendarificConfiguration.getApiKey())
                .queryParam("country", country)
                .queryParam("year", year);

        ResponseEntity<CalendarificResponseDto> resp = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                null,
                CalendarificResponseDto.class);

        return resp.getBody();
    }
}
