package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.OpenFdaResponseDto;
import com.rest.private_medical_clinic.service.OpenFdaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/medicines")
@RequiredArgsConstructor
public class OpenFdaController {

    private final OpenFdaService openFdaService;

    @GetMapping("/{drugName}")
    public ResponseEntity<OpenFdaResponseDto.DrugLabelDto> getDrugSpecification(@PathVariable String drugName) {
        return ResponseEntity.ok(openFdaService.getDrug(drugName));
    }
}
