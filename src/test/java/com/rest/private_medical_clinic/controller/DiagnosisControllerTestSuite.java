package com.rest.private_medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.facade.DiagnosisFacade;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(DiagnosisController.class)
public class DiagnosisControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DiagnosisFacade diagnosisFacade;

    @Test
    public void shouldGetEmptyDiagnosisList() throws Exception {
        //Given
        when(diagnosisFacade.getAllDiagnosis()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .get("/v1/diagnoses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldGetDiagnosisList() throws Exception {
        //Given
        List<DiagnosisDto> diagnosisDtoList = List.of(new DiagnosisDto(1L, 1L,
                "The test results have improved significantly",
                "Rest and painkillers for headaches", LocalDateTime.now(),
                "Low Dose Aspirin Enteric Safety-Coated", "ASPIRIN",
                "Directions drink a full glass of water with each dose adults" +
                        " and children 12 years and over: take 4 to 8 tablets every 4 hours not to exceed 48 tablets" +
                        " in 24 hours unless directed by a doctor children under 12 years: consult a doctor"));
        when(diagnosisFacade.getAllDiagnosis()).thenReturn(diagnosisDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/diagnoses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldGetDiagnosisById() throws Exception {
        //Given
        long diagnosisId = 1L;
        DiagnosisDto diagnosisDto = new DiagnosisDto(1L, 1L,
                "The test results have improved significantly",
                "Rest and painkillers for headaches", LocalDateTime.now(),
                "Low Dose Aspirin Enteric Safety-Coated", "ASPIRIN",
                "Directions drink a full glass of water with each dose adults" +
                        " and children 12 years and over: take 4 to 8 tablets every 4 hours not to exceed 48 tablets" +
                        " in 24 hours unless directed by a doctor children under 12 years: consult a doctor");
        when(diagnosisFacade.getDiagnosisById(diagnosisId)).thenReturn(diagnosisDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .get("/v1/diagnoses/{diagnosisId}", diagnosisId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.appointmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.generic_name", Matchers.is("ASPIRIN")));

    }

    @Test
    public void shouldGetDiagnosisListByPatientId() throws Exception {
        //Given
        long patientId = 2L;
        List<DiagnosisDto> diagnosisDtoList = List.of(new DiagnosisDto(1L, 1L,
                "The test results have improved significantly",
                "Rest and painkillers for headaches", LocalDateTime.now(),
                "Low Dose Aspirin Enteric Safety-Coated", "ASPIRIN",
                "Directions drink a full glass of water with each dose adults" +
                        " and children 12 years and over: take 4 to 8 tablets every 4 hours not to exceed 48 tablets" +
                        " in 24 hours unless directed by a doctor children under 12 years: consult a doctor"));
        when(diagnosisFacade.getDiagnosisByPatientId(patientId)).thenReturn(diagnosisDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/diagnoses/patient/{patientId}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].appointmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].generic_name", Matchers.is("ASPIRIN")));

    }

    @Test
    public void shouldGetDiagnosisListByDoctorId() throws Exception {
        //Given
        long doctorId = 1L;
        List<DiagnosisDto> diagnosisDtoList = List.of(new DiagnosisDto(1L, 1L,
                "The test results have improved significantly",
                "Rest and painkillers for headaches", LocalDateTime.now(),
                "Low Dose Aspirin Enteric Safety-Coated", "ASPIRIN",
                "Directions drink a full glass of water with each dose adults" +
                        " and children 12 years and over: take 4 to 8 tablets every 4 hours not to exceed 48 tablets" +
                        " in 24 hours unless directed by a doctor children under 12 years: consult a doctor"));
        when(diagnosisFacade.getDiagnosisByDoctorId(doctorId)).thenReturn(diagnosisDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/diagnoses/doctor/{doctorId}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].appointmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].generic_name", Matchers.is("ASPIRIN")));

    }

    @Test
    public void shouldGetDiagnosisByAppointmentId() throws Exception {
        //Given
        long appointmentId = 1L;
        DiagnosisDto diagnosisDto = new DiagnosisDto(1L, 1L,
                "The test results have improved significantly",
                "Rest and painkillers for headaches", LocalDateTime.now(),
                "Low Dose Aspirin Enteric Safety-Coated", "ASPIRIN",
                "Directions drink a full glass of water with each dose adults" +
                        " and children 12 years and over: take 4 to 8 tablets every 4 hours not to exceed 48 tablets" +
                        " in 24 hours unless directed by a doctor children under 12 years: consult a doctor");
        when(diagnosisFacade.getDiagnosisByAppointmentId(appointmentId)).thenReturn(diagnosisDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/diagnoses/appointment/{appointmentId}", appointmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.appointmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.generic_name", Matchers.is("ASPIRIN")));

    }

    @Test
    public void shouldDeleteDiagnosis() throws Exception {
        //Given
        long diagnosisId = 1L;

        doNothing().when(diagnosisFacade).deleteDiagnosis(diagnosisId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .delete("/v1/diagnoses/{diagnosisId}", diagnosisId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldUpdateDiagnosis() throws Exception {
        //Given
        DiagnosisDto updatedDiagnosisDto = new DiagnosisDto(1L, 1L,
                "Updated description",
                "Rest and painkillers for headaches", LocalDateTime.now(),
                "Low Dose Aspirin Enteric Safety-Coated", "ASPIRIN",
                "Directions drink a full glass of water with each dose adults" +
                        " and children 12 years and over: take 4 to 8 tablets every 4 hours not to exceed 48 tablets" +
                        " in 24 hours unless directed by a doctor children under 12 years: consult a doctor");
        when(diagnosisFacade.updateDiagnosis(updatedDiagnosisDto)).thenReturn(updatedDiagnosisDto);

        String jsonContent = objectMapper.writeValueAsString(updatedDiagnosisDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/diagnoses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.appointmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Updated description")));
    }
}
