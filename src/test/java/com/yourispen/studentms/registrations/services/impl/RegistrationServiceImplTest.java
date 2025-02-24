package com.yourispen.studentms.registrations.services.impl;


import com.yourispen.studentms.exception.EntityNotFoundException;
import com.yourispen.studentms.registrations.dto.requests.RegistrationDtoRequest;
import com.yourispen.studentms.registrations.dto.responses.RegistrationDtoResponse;
import com.yourispen.studentms.registrations.entities.RegistrationEntity;
import com.yourispen.studentms.registrations.mapper.RegistrationMapper;
import com.yourispen.studentms.registrations.repository.RegistrationRepository;
import com.yourispen.studentms.students.entities.StudentEntity;
import com.yourispen.studentms.students.repository.StudentRepository;
import com.yourispen.studentms.classes.entities.ClassEntity;
import com.yourispen.studentms.classes.repository.ClassRepository;
import com.yourispen.studentms.academicyears.entities.AcademicYearEntity;
import com.yourispen.studentms.academicyears.repository.AcademicYearRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;


import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {


    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private AcademicYearRepository academicYearRepository;

    @Mock
    private RegistrationMapper registrationMapper;

    @Mock
    private MessageSource messageSource;


    @Test
    void saveRegistrationOK() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(getStudentEntity()));
        when(classRepository.findById(anyLong())).thenReturn(Optional.of(getClassEntity()));
        when(academicYearRepository.findById(anyLong())).thenReturn(Optional.of(createAcademicYearEntity()));
        when(registrationMapper.toRegistrationEntity(any())).thenReturn(getRegistrationEntity());
        when(registrationRepository.save(any())).thenReturn(getRegistrationEntity());
        when(registrationMapper.toRegistrationDtoResponse(any())).thenReturn(getRegistrationDtoResponse());


        Optional<RegistrationDtoResponse> savedRegistration = registrationService.saveRegistration(getRegistrationDtoRequest());
        assertTrue(savedRegistration.isPresent());
    }

    private ClassEntity getClassEntity() {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(1L);
        // Set other properties as required
        return classEntity;
    }


    /*@Test
    void saveRegistrationKO() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("student.notfound"), any(), any(Locale.class)))
                .thenReturn("Étudiant non trouvé");


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.saveRegistration(getRegistrationDtoRequest()));


        assertEquals("Étudiant non trouvé", exception.getMessage());
    }*/


    @Test
    void getAllRegistrations() {
        when(registrationRepository.findAll()).thenReturn(List.of(getRegistrationEntity()));
        when(registrationMapper.toRegistrationDtoResponseList(any())).thenReturn(List.of(getRegistrationDtoResponse()));


        List<RegistrationDtoResponse> registrations = registrationService.getAllRegistrations();
        assertFalse(registrations.isEmpty());
        assertEquals(1, registrations.size());
    }


    @Test
    void getRegistrationByIdOK() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(getRegistrationEntity()));
        when(registrationMapper.toRegistrationDtoResponse(any())).thenReturn(getRegistrationDtoResponse());


        Optional<RegistrationDtoResponse> registration = registrationService.getRegistrationById(1L);
        assertTrue(registration.isPresent());
    }


    @Test
    void getRegistrationByIdKO() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("registration.notfound"), any(), any(Locale.class)))
                .thenReturn("Inscription non trouvée");


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.getRegistrationById(1L));


        assertEquals("Inscription non trouvée", exception.getMessage());
    }


    @Test
    void deleteRegistrationOK() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(getRegistrationEntity()));


        boolean result = registrationService.deleteRegistration(1L);
        assertTrue(result);
        verify(registrationRepository, times(1)).deleteById(anyLong());
    }


    /*@Test
    void deleteRegistrationKO() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.deleteRegistration(1L));


        assertEquals("Inscription non trouvée", exception.getMessage());
    }*/


    private RegistrationDtoRequest getRegistrationDtoRequest() {
        return new RegistrationDtoRequest(new Date(), "Nouvelle inscription", false, 1L, 1L, 1L);
    }


    private RegistrationDtoResponse getRegistrationDtoResponse() {
        return new RegistrationDtoResponse(1L, new Date(), "Nouvelle inscription", false, 1L, 1L, 1L);
    }


    private RegistrationEntity getRegistrationEntity() {
        RegistrationEntity entity = new RegistrationEntity();
        entity.setId(1L);
        entity.setDate(new Date());
        entity.setDescription("Nouvelle inscription");
        entity.setArchive(false);
        entity.setStudent(getStudentEntity());
        entity.setClassEntity(getClassEntity());
        entity.setAcademicYear(createAcademicYearEntity());
        return entity;
    }
    private StudentEntity getStudentEntity() {
        StudentEntity student = new StudentEntity();
        student.setId(1L);
        student.setEmailPro("student@example.com");
        // Set other properties as required
        return student;
    }
    private AcademicYearEntity createAcademicYearEntity() {
        AcademicYearEntity academicYearEntity = new AcademicYearEntity();
        academicYearEntity.setId(1L);
        // Set other properties as required
        return academicYearEntity;
    }
}
