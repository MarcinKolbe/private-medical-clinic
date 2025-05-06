package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.dto.CalendarificResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayMapper {

    public List<LocalDate> mapToLocalDate(CalendarificResponseDto dto) {

        if (dto == null || dto.getResponse() == null) {
            return Collections.emptyList();
        }
        // Mapuję ISO‐stringi na LocalDate
        return dto.getResponse().getHolidays().stream()
                .map(h -> {
                    String iso = h.getDate().getIso();
                    try {
                        // próba parsowania pełnego formatu z offsetem
                        return OffsetDateTime.parse(iso).toLocalDate();
                    } catch (DateTimeParseException e) {
                        // fallback dla „czystej” daty
                        return LocalDate.parse(iso);
                    }
                })
                .toList();
    }
}
