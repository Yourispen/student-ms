package com.yourispen.studentms.students.services.impl;

import com.yourispen.studentms.exception.EntityExistsException;
import com.yourispen.studentms.exception.EntityNotFoundException;
import com.yourispen.studentms.registrations.entities.RegistrationEntity;
import com.yourispen.studentms.students.dto.requests.StudentDtoRequest;
import com.yourispen.studentms.students.dto.responses.StudentDtoResponse;
import com.yourispen.studentms.students.entities.StudentEntity;
import com.yourispen.studentms.students.mapper.StudentMapper;
import com.yourispen.studentms.students.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private StudentServiceImpl studentService;

    private StudentEntity studentEntity;
    private StudentDtoRequest studentDtoRequest;
    private StudentDtoResponse studentDtoResponse;
    private RegistrationEntity registration1;
    private RegistrationEntity registration2;

    @BeforeEach
    void setUp() {
        registration1 = new RegistrationEntity();
        registration1.setId(10L);

        registration2 = new RegistrationEntity();
        registration2.setId(20L);

        studentEntity = new StudentEntity();
        studentEntity.setId(1L);
        studentEntity.setFirstName("Amadou");
        studentEntity.setLastName("Ba");
        studentEntity.setEmailPro("amadou.ba@school.com");
        studentEntity.setToken("abc123");
        studentEntity.setEmailPerso("amadou.ba@gmail.com");
        studentEntity.setPhoneNumber("781234567");
        studentEntity.setAddress("Dakar, Sénégal");
        studentEntity.setArchive(false);
        studentEntity.setRegistrations(List.of(registration1, registration2));

        studentDtoRequest = new StudentDtoRequest();
        studentDtoRequest.setFirstName("Amadou");
        studentDtoRequest.setLastName("Ba");
        studentDtoRequest.setEmailPro("amadou.ba@school.com");
        studentDtoRequest.setToken("abc123");
        studentDtoRequest.setEmailPerso("amadou.ba@gmail.com");
        studentDtoRequest.setPhoneNumber("781234567");
        studentDtoRequest.setAddress("Dakar, Sénégal");
        studentDtoRequest.setArchive(false);

        studentDtoResponse = new StudentDtoResponse();
        studentDtoResponse.setId(1L);
        studentDtoResponse.setFirstName("Amadou");
        studentDtoResponse.setLastName("Ba");
        studentDtoResponse.setEmailPro("amadou.ba@school.com");
        studentDtoResponse.setToken("abc123");
        studentDtoResponse.setEmailPerso("amadou.ba@gmail.com");
        studentDtoResponse.setPhoneNumber("781234567");
        studentDtoResponse.setAddress("Dakar, Sénégal");
        studentDtoResponse.setArchive(false);
        studentDtoResponse.setRegistrationNu("ISI0000001");
        studentDtoResponse.setRegistrationIds(List.of(10L, 20L)); // Simuler des inscriptions
    }

    @Test
    void saveStudentOK() {
        lenient().when(studentRepository.findByEmailPro(anyString())).thenReturn(Optional.empty());
        lenient().when(studentRepository.count()).thenReturn(0L); // Simuler aucun étudiant existant
        lenient().when(studentMapper.toStudentEntity(any())).thenReturn(studentEntity);
        when(studentRepository.save(any())).thenReturn(studentEntity);
        when(studentMapper.toStudentDtoResponse(any())).thenReturn(studentDtoResponse);

        Optional<StudentDtoResponse> savedStudent = studentService.saveStudent(studentDtoRequest);

        assertTrue(savedStudent.isPresent());
        assertEquals("Amadou", savedStudent.get().getFirstName());
        assertEquals("ISI0000001", savedStudent.get().getRegistrationNu()); // Vérifier la génération automatique
        verify(studentRepository, times(1)).save(any());
    }





    @Test
    void saveStudentKO_AlreadyExists() {
        when(studentRepository.findByEmailPro(anyString())).thenReturn(Optional.of(studentEntity));
        when(messageSource.getMessage(eq("student.exists"), any(), any(Locale.class)))
                .thenReturn("L'étudiant avec l'email = amadou.ba@school.com est déjà inscrit");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> studentService.saveStudent(studentDtoRequest));

        assertEquals("L'étudiant avec l'email = amadou.ba@school.com est déjà inscrit", exception.getMessage());
        verify(studentRepository, never()).save(any());
    }

    @Test
    void getAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(studentEntity));
        when(studentMapper.toStudentDtoResponseList(any())).thenReturn(List.of(studentDtoResponse));

        List<StudentDtoResponse> students = studentService.getAllStudents();

        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentByIdOK() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(studentEntity));
        when(studentMapper.toStudentDtoResponse(any())).thenReturn(studentDtoResponse);

        Optional<StudentDtoResponse> student = studentService.getStudentById(1L);

        assertTrue(student.isPresent());
        assertEquals("Amadou", student.get().getFirstName());
        assertEquals(1L, student.get().getId());
        assertEquals(List.of(10L, 20L), student.get().getRegistrationIds()); // Vérifier les inscriptions
        verify(studentRepository, times(1)).findById(anyLong());
    }

    @Test
    void getStudentByIdKO_NotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("student.notfound"), any(), any(Locale.class)))
                .thenReturn("Étudiant non trouvé");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> studentService.getStudentById(1L));

        assertEquals("Étudiant non trouvé", exception.getMessage());
        verify(studentRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteStudentOK() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(studentEntity));

        boolean result = studentService.deleteStudent(1L);

        assertTrue(result);
        verify(studentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteStudentKO_NotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("student.notfound"), any(), any(Locale.class)))
                .thenReturn("Étudiant non trouvé");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> studentService.deleteStudent(1L));

        assertEquals("Étudiant non trouvé", exception.getMessage());
        verify(studentRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateStudentOK() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(studentEntity));
        when(studentRepository.save(any())).thenReturn(studentEntity);
        when(studentMapper.toStudentDtoResponse(any())).thenReturn(studentDtoResponse);

        Optional<StudentDtoResponse> updatedStudent = studentService.updateStudent(1L, studentDtoRequest);

        assertTrue(updatedStudent.isPresent());
        assertEquals("Amadou", updatedStudent.get().getFirstName());
        assertEquals(1L, updatedStudent.get().getId());
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    void updateStudentKO_NotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("student.notfound"), any(), any(Locale.class)))
                .thenReturn("Étudiant non trouvé");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> studentService.updateStudent(1L, studentDtoRequest));

        assertEquals("Étudiant non trouvé", exception.getMessage());
        verify(studentRepository, times(1)).findById(anyLong());
    }
}


