package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.OpenFdaResponseDto;
import com.rest.private_medical_clinic.facade.ExternalDataFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/medicines")
@RequiredArgsConstructor
public class OpenFdaController {

    private final ExternalDataFacade externalDataFacade;

    @GetMapping("/{drugName}")
    public ResponseEntity<OpenFdaResponseDto.DrugLabelDto> getDrugSpecification(@PathVariable String drugName) {
        return ResponseEntity.ok(externalDataFacade.getDrugInfo(drugName));
    }
}
