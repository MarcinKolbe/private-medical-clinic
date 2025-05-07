package com.rest.private_medical_clinic.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFdaResponseDto {

    private List<DrugLabelDto> results;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DrugLabelDto {
        private List<String> dosage_and_administration;
        private OpenFdaDto openfda;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OpenFdaDto {
        private List<String> brand_name;
        private List<String> generic_name;
    }
}
