package com.rest.private_medical_clinic.integration;

import com.rest.private_medical_clinic.domain.*;
import com.rest.private_medical_clinic.domain.dto.*;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.enums.UserRole;
import com.rest.private_medical_clinic.exception.*;
import com.rest.private_medical_clinic.repository.*;
import com.rest.private_medical_clinic.service.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class AppointmentLifecycleIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DiagnosisService diagnosisService;
    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorScheduleTemplateService doctorScheduleTemplateService;
    @Autowired
    private AppointmentService appointmentService;

    @Test
    @Transactional
    void testFullAppointmentLifecycle() {
        //preparing doctor and patient
        User userDoctor = new User();
        userDoctor.setUsername("docH");
        userDoctor.setPassword("pass");
        userDoctor.setMail("docH@mail.com");
        userDoctor.setUserRole(UserRole.DOCTOR);
        userDoctor.setCreated_at(LocalDate.now());
        userDoctor.setBlocked(false);

        Doctor doctor = new Doctor();
        doctor.setFirstname("Gregory");
        doctor.setLastname("House");
        doctor.setSpecialization("Diagnostics");
        doctor.setUser(userDoctor);
        userDoctor.setDoctor(doctor);
        userRepository.save(userDoctor);

        User userPatient = new User();
        userPatient.setUsername("janek58");
        userPatient.setPassword("pass");
        userPatient.setMail("janek58@mail.com");
        userPatient.setUserRole(UserRole.PATIENT);
        userPatient.setCreated_at(LocalDate.now());
        userPatient.setBlocked(false);

        Patient patient = new Patient();
        patient.setFirstname("Jan");
        patient.setLastname("Kowalski");
        patient.setPhoneNumber(500500500);
        patient.setPesel("58062411204");
        patient.setBirthDate(LocalDate.of(1958,6,24));
        patient.setUser(userPatient);
        userPatient.setPatient(patient);
        userRepository.save(userPatient);

            //trying to find a doctor who doesn't exist
            assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctor(15));
            assertDoesNotThrow(() -> doctorService.getDoctor(doctor.getId()));

            //trying to find a patient who doesn't exist
            assertThrows(PatientNotFoundException.class, () -> patientService.getPatient(15));
            assertDoesNotThrow(() -> patientService.getPatient(patient.getId()));

            //trying to find a user who doesn't exist
            assertThrows(UserNotFoundException.class, () -> userService.getUserById(15));
            assertDoesNotThrow(() -> userService.getUserById(userDoctor.getId()));

        //preparing doctor schedule template
        DoctorScheduleTemplateDto doctorScheduleTemplateDto = new DoctorScheduleTemplateDto();
        doctorScheduleTemplateDto.setDayOfWeek(DayOfWeek.MONDAY);
        doctorScheduleTemplateDto.setStartTime(LocalTime.of(15,0,0));
        doctorScheduleTemplateDto.setEndTime(LocalTime.of(18,0,0));

        DoctorScheduleTemplate template = doctorScheduleTemplateService.createDoctorScheduleTemplate(doctor.getId(), doctorScheduleTemplateDto);

        //check if there are any doctor's availability
        List<DoctorAvailability> availabilities = doctorAvailabilityService.getAllAvailabilityForAllDoctors();
        DoctorAvailability availability = availabilities.get(0);
        assertNotNull(availabilities);
        assertThrows(DoctorAvailabilityException.class, () -> doctorAvailabilityService.getDoctorAvailabilityById(15L));
        assertDoesNotThrow(() -> doctorAvailabilityService.getDoctorAvailabilityById(availability.getId()));

        //trying to find a template that doesn't exist
        assertThrows(DoctorScheduleTemplateException.class,
                () -> doctorScheduleTemplateService.getDoctorScheduleTemplateById(15L));
        assertDoesNotThrow(() -> doctorScheduleTemplateService.getDoctorScheduleTemplateById(template.getId()));

        //register appointment
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        AppointmentRegistrationDto appointmentRegistrationDto = new AppointmentRegistrationDto(doctor.getId(),
                patient.getId(), nextMonday, LocalTime.of(15,0,0), "fever");
        Appointment appointment = appointmentService.createAppointment(appointmentRegistrationDto);

        assertNotNull(appointment);
        assertEquals(AppointmentStatus.SCHEDULED, appointment.getStatus());

        //trying to find an appointment that doesn't exist
        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.getAppointmentById(15));
        assertDoesNotThrow(() -> appointmentService.getAppointmentById(appointment.getId()));

        //cancel appointment
        appointmentService.cancelAppointment(appointment.getId());
        Appointment cancelledAppointment = appointmentService.getAppointmentById(appointment.getId());

        assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());

        //reschedule appointment
        Appointment newAppointment = appointmentService.createAppointment(appointmentRegistrationDto);

        assertNotNull(newAppointment);
        assertEquals(AppointmentStatus.SCHEDULED, newAppointment.getStatus());

        AppointmentRescheduleDto rescheduleDto = new AppointmentRescheduleDto(nextMonday, LocalTime.of(16,0,0));
        Appointment rescheduledAppointment = appointmentService.rescheduleAppointment(newAppointment.getId(), rescheduleDto);

        assertEquals(AppointmentStatus.RESCHEDULED, rescheduledAppointment.getStatus());
        assertEquals(LocalTime.of(16,0,0), rescheduledAppointment.getTime());

        //add diagnosis to appointment
        DiagnosisDto diagnosisDto = new DiagnosisDto();
        diagnosisDto.setDescription("cold");
        diagnosisDto.setRecommendations("rest");
        diagnosisDto.setGeneric_name("ASPIRIN");
        appointmentService.addDiagnosisToAppointment(rescheduledAppointment.getId(), diagnosisDto);

        assertNotNull(rescheduledAppointment.getDiagnosis());
        assertEquals(AppointmentStatus.COMPLETED, rescheduledAppointment.getStatus());

        //trying to find a diagnosis that doesn't exist
        assertThrows(DiagnosisNotFoundException.class, () -> diagnosisService.getDiagnosisById(15));
        List<Diagnosis> diagnoses = diagnosisService.getAllDiagnosis();
        assertDoesNotThrow(() -> diagnosisService.getDiagnosisById(diagnoses.getFirst().getId()));

        //add review to appointment
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setAppointmentId(rescheduledAppointment.getId());
        reviewDto.setRating(5);
        reviewDto.setComment("happy");

        Review review = reviewService.createReview(reviewDto);

        assertNotNull(review);
        assertEquals("happy", review.getComment());

        //trying to find a review that doesn't exist
        assertThrows(ReviewNotFoundException.class, () -> reviewService.getReviewById(15));
        assertDoesNotThrow(() -> reviewService.getReviewById(review.getId()));
    }
}
