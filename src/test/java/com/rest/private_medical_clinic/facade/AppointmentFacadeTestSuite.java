package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.*;
import com.rest.private_medical_clinic.domain.dto.AppointmentDto;
import com.rest.private_medical_clinic.domain.dto.AppointmentRegistrationDto;
import com.rest.private_medical_clinic.domain.dto.AppointmentRescheduleDto;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.mapper.AppointmentMapper;
import com.rest.private_medical_clinic.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentFacadeTestSuite {

    @InjectMocks
    private AppointmentFacade appointmentFacade;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Test
    void shouldGetEmptyAppointmentList() {
        //Given
        List<Appointment> appointmentList = List.of(new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review()));

        when(appointmentService.getAllAppointments()).thenReturn(appointmentList);
        when(appointmentMapper.mapToDtoList(anyList())).thenReturn(List.of());

        //When
        List<AppointmentDto> appointmentDtoList = appointmentFacade.getAllAppointments();

        //Then
        assertNotNull(appointmentDtoList);
        assertEquals(0, appointmentDtoList.size());
    }

    @Test
    void shouldGetAllAppointments() {
        //Given
        List<Appointment> appointmentList = List.of(new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review()));
        List<AppointmentDto> appointmentDtoList = List.of(new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", 1L));

        when(appointmentService.getAllAppointments()).thenReturn(appointmentList);
        when(appointmentMapper.mapToDtoList(anyList())).thenReturn(appointmentDtoList);

        //When
        List<AppointmentDto> appointmentDtos = appointmentFacade.getAllAppointments();

        //Then
        assertNotNull(appointmentDtos);
        assertEquals(1, appointmentDtos.size());
    }

    @Test
    void shouldGetAllAppointmentsByStatus() {
        //Given
        List<Appointment> appointmentList = List.of(new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review()));
        List<AppointmentDto> appointmentDtoList = List.of(new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", 1L));

        when(appointmentService.getAppointmentsByStatus("SCHEDULED")).thenReturn(appointmentList);
        when(appointmentMapper.mapToDtoList(anyList())).thenReturn(appointmentDtoList);

        //When
        List<AppointmentDto> appointmentDtos = appointmentFacade.getAppointmentsByStatus("SCHEDULED");

        //Then
        assertNotNull(appointmentDtos);
        assertEquals(1, appointmentDtos.size());
    }

    @Test
    void shouldGetAppointmentById() {
        //Given
        Appointment appointment = new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review());
        AppointmentDto appointmentDto = new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", 1L);

        when(appointmentService.getAppointmentById(appointment.getId())).thenReturn(appointment);
        when(appointmentMapper.mapToDto(appointment)).thenReturn(appointmentDto);

        //When
        AppointmentDto appointmentDtoById = appointmentFacade.getAppointmentById(appointment.getId());

        //Then
        assertEquals(1L, appointmentDtoById.getId());
        assertEquals(LocalDate.of(2025, 5, 5), appointmentDtoById.getDate());
        assertEquals("notes", appointmentDtoById.getNotes());
    }

    @Test
    void shouldCreateAppointment() {
        //Given
        Appointment appointment = new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review());
        AppointmentDto appointmentDto = new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", 1L);
        AppointmentRegistrationDto appointmentRegistrationDto = new AppointmentRegistrationDto(1L, 2L,
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0), "notes");

        when(appointmentService.createAppointment(any(AppointmentRegistrationDto.class))).thenReturn(appointment);
        when(appointmentMapper.mapToDto(any(Appointment.class))).thenReturn(appointmentDto);

        //When
        AppointmentDto createdAppointment = appointmentFacade.createAppointment(appointmentRegistrationDto);

        //Then
        verify(appointmentService, times(1)).createAppointment(appointmentRegistrationDto);

    }

    @Test
    void shouldCancelAppointment() {
        //Given
        Appointment appointment = new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review());

        doNothing().when(appointmentService).cancelAppointment(appointment.getId());

        //When
        appointmentFacade.cancelAppointment(appointment.getId());

        //Then
        verify(appointmentService, times(1)).cancelAppointment(appointment.getId());
    }

    @Test
    void shouldRescheduleAppointment() {
        //Given
        Appointment appointment = new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review());
        AppointmentDto appointmentDto = new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", 1L);
        AppointmentRescheduleDto appointmentRescheduleDto = new AppointmentRescheduleDto(
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0));

        when(appointmentService.rescheduleAppointment(1L, appointmentRescheduleDto)).thenReturn(appointment);
        when(appointmentMapper.mapToDto(appointment)).thenReturn(appointmentDto);

        //When
        AppointmentDto rescheduledAppointment = appointmentFacade.rescheduleAppointment(1L, appointmentRescheduleDto);

        //Then
        verify(appointmentService, times(1)).rescheduleAppointment(1L, appointmentRescheduleDto);
        assertEquals(LocalDate.of(2025, 5 ,5), rescheduledAppointment.getDate());
    }

    @Test
    void shouldDeleteAppointment() {
        //Given
        Appointment appointment = new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review());

        doNothing().when(appointmentService).deleteAppointmentById(appointment.getId());

        //When
        appointmentFacade.deleteAppointment(appointment.getId());

        //Then
        verify(appointmentService, times(1)).deleteAppointmentById(appointment.getId());
    }

    @Test
    void shouldGetAppointmentsByDoctor() {
        //Given
        long doctorId = 1L;
        List<Appointment> appointmentList = List.of(new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review()));
        List<AppointmentDto> appointmentDtoList = List.of(new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", 1L));

        when(appointmentService.getAppointmentsForDoctor(doctorId)).thenReturn(appointmentList);
        when(appointmentMapper.mapToDtoList(appointmentList)).thenReturn(appointmentDtoList);

        //When
        List<AppointmentDto> retrievedAppointmentsDto = appointmentFacade.getAppointmentsByDoctorId(doctorId);

        //Then
        assertEquals(1, retrievedAppointmentsDto.size());
    }

    @Test
    void shouldGetAppointmentsByPatient() {
        //Given
        long patientId = 1L;
        List<Appointment> appointmentList = List.of(new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review()));
        List<AppointmentDto> appointmentDtoList = List.of(new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", 1L));

        when(appointmentService.getAppointmentsForPatient(patientId)).thenReturn(appointmentList);
        when(appointmentMapper.mapToDtoList(appointmentList)).thenReturn(appointmentDtoList);

        //When
        List<AppointmentDto> retrievedAppointmentsDto = appointmentFacade.getAppointmentsByPatientId(patientId);

        //Then
        assertEquals(1, retrievedAppointmentsDto.size());
    }

    @Test
    void shouldGetAppointmentsByDate() {
        //Given
        LocalDate date = LocalDate.of(2025, 5 ,5);
        List<Appointment> appointmentList = List.of(new Appointment(1L, new Doctor(), new Patient(),
                LocalDate.of(2025, 5 ,5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", new Diagnosis(), new Review()));
        List<AppointmentDto> appointmentDtoList = List.of(new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "notes", 1L));

        when(appointmentService.getAllAppointmentsByDate(date)).thenReturn(appointmentList);
        when(appointmentMapper.mapToDtoList(appointmentList)).thenReturn(appointmentDtoList);

        //When
        List<AppointmentDto> retrievedAppointmentsDto = appointmentFacade.getAppointmentsByDate(date);

        //Then
        assertEquals(1, retrievedAppointmentsDto.size());
    }

    @Test
    void shouldAddDiagnosisToAppointment() {
        //Given
        long appointmentId = 1L;
        DiagnosisDto diagnosisDto = new DiagnosisDto();

        doNothing().when(appointmentService).addDiagnosisToAppointment(appointmentId, diagnosisDto);

        //When
        appointmentFacade.addDiagnosisToAppointment(appointmentId, diagnosisDto);

        //Then
        verify(appointmentService).addDiagnosisToAppointment(appointmentId, diagnosisDto);
    }
}
