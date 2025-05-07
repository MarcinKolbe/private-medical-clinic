package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.client.OpenFdaClient;
import com.rest.private_medical_clinic.domain.dto.OpenFdaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenFdaService {

    private final OpenFdaClient openFdaClient;

        public OpenFdaResponseDto.DrugLabelDto getDrug(String drugName) {
            return openFdaClient.fetchDrugLabel(drugName);
        }
}
