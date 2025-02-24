package com.yourispen.studentms.registrations.services.impl;


import com.yourispen.studentms.exception.EntityNotFoundException;
import com.yourispen.studentms.registrations.dto.requests.RegistrationDtoRequest;
import com.yourispen.studentms.registrations.dto.responses.RegistrationDtoResponse;
import com.yourispen.studentms.registrations.entities.RegistrationEntity;
import com.yourispen.studentms.registrations.mapper.RegistrationMapper;
import com.yourispen.studentms.registrations.repository.RegistrationRepository;
import com.yourispen.studentms.registrations.services.RegistrationService;
import com.yourispen.studentms.students.entities.StudentEntity;
import com.yourispen.studentms.students.repository.StudentRepository;
import com.yourispen.studentms.classes.entities.ClassEntity;
import com.yourispen.studentms.classes.repository.ClassRepository;
import com.yourispen.studentms.academicyears.entities.AcademicYearEntity;
import com.yourispen.studentms.academicyears.repository.AcademicYearRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
@Getter
@Setter
public class RegistrationServiceImpl implements RegistrationService {


    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final AcademicYearRepository academicYearRepository;
    private final RegistrationMapper registrationMapper;
    private final MessageSource messageSource;


    @Override
    @Transactional
    public Optional<RegistrationDtoResponse> saveRegistration(RegistrationDtoRequest registrationDto) {
        StudentEntity student = studentRepository.findById(registrationDto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("L'étudiant n'existe pas"));


        ClassEntity classEntity = classRepository.findById(registrationDto.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("La classe n'existe pas"));


        AcademicYearEntity academicYear = academicYearRepository.findById(registrationDto.getAcademicYearId())
                .orElseThrow(() -> new EntityNotFoundException("L'année académique n'existe pas"));


        RegistrationEntity registration = registrationMapper.toRegistrationEntity(registrationDto);
        registration.setStudent(student);
        registration.setClassEntity(classEntity);
        registration.setAcademicYear(academicYear);


        RegistrationEntity savedRegistration = registrationRepository.save(registration);
        return Optional.of(registrationMapper.toRegistrationDtoResponse(savedRegistration));
    }


    @Override
    public Optional<RegistrationDtoResponse> getRegistrationById(Long id) {
        return registrationRepository.findById(id)
                .map(registrationMapper::toRegistrationDtoResponse)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("registration.notfound", new Object[]{id}, Locale.getDefault())));
    }


    @Override
    public List<RegistrationDtoResponse> getAllRegistrations() {
        List<RegistrationEntity> registrations = registrationRepository.findAll();
        return registrationMapper.toRegistrationDtoResponseList(registrations);
    }


    @Override
    @Transactional
    public boolean deleteRegistration(Long id) {
        if (registrationRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("registration.notfound", new Object[]{id}, Locale.getDefault()));
        }
        registrationRepository.deleteById(id);
        return true;
    }


    @Override
    @Transactional
    public Optional<RegistrationDtoResponse> updateRegistration(Long id, RegistrationDtoRequest registrationDto) {
        return registrationRepository.findById(id)
                .map(registration -> {
                    StudentEntity student = studentRepository.findById(registrationDto.getStudentId())
                            .orElseThrow(() -> new EntityNotFoundException("L'étudiant n'existe pas"));


                    ClassEntity classEntity = classRepository.findById(registrationDto.getClassId())
                            .orElseThrow(() -> new EntityNotFoundException("La classe n'existe pas"));


                    AcademicYearEntity academicYear = academicYearRepository.findById(registrationDto.getAcademicYearId())
                            .orElseThrow(() -> new EntityNotFoundException("L'année académique n'existe pas"));


                    registration.setStudent(student);
                    registration.setClassEntity(classEntity);
                    registration.setAcademicYear(academicYear);
                    registration.setDescription(registrationDto.getDescription()); // Ajout de la description


                    RegistrationEntity updatedRegistration = registrationRepository.save(registration);
                    return Optional.of(registrationMapper.toRegistrationDtoResponse(updatedRegistration));
                }).orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("registration.notfound", new Object[]{id}, Locale.getDefault())));
    }
}





