package com.yourispen.studentms.students.services.impl;

import com.yourispen.studentms.exception.EntityExistsException;
import com.yourispen.studentms.exception.EntityNotFoundException;
import com.yourispen.studentms.students.dto.requests.StudentDtoRequest;
import com.yourispen.studentms.students.dto.responses.StudentDtoResponse;
import com.yourispen.studentms.students.entities.StudentEntity;
import com.yourispen.studentms.students.mapper.StudentMapper;
import com.yourispen.studentms.students.repository.StudentRepository;
import com.yourispen.studentms.students.services.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private static final String STUDENT_NOTFOUND = "student.notfound";
    private static final String STUDENT_EXISTS = "student.exists";

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public Optional<StudentDtoResponse> saveStudent(StudentDtoRequest studentDto) {

        // Vérifier si l'étudiant existe déjà
        if (studentRepository.findByEmailPro(studentDto.getEmailPro()).isPresent()) {
            throw new EntityExistsException(
                    messageSource.getMessage(STUDENT_EXISTS, new Object[]{studentDto.getEmailPro()}, Locale.getDefault()));
        }

        // Mapper le DTO vers l'entité
        StudentEntity student = studentMapper.toStudentEntity(studentDto);

        // Générer automatiquement le `registrationNu`
        student.setRegistrationNu(generateRegistrationNumber());

        // Sauvegarde en base de données
        StudentEntity savedStudent = studentRepository.save(student);
        return Optional.of(studentMapper.toStudentDtoResponse(savedStudent));
    }

    @Override
    public List<StudentDtoResponse> getAllStudents() {
        List<StudentEntity> students = studentRepository.findAll();
        return studentMapper.toStudentDtoResponseList(students);
    }

    @Override
    public Optional<StudentDtoResponse> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toStudentDtoResponse)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage(STUDENT_NOTFOUND, new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public boolean deleteStudent(Long id) {
        if (studentRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(
                    messageSource.getMessage(STUDENT_NOTFOUND, new Object[]{id}, Locale.getDefault()));
        }
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<StudentDtoResponse> updateStudent(Long id, StudentDtoRequest studentDto) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(studentDto.getFirstName());
                    student.setToken(studentDto.getToken());
                    student.setLastName(studentDto.getLastName());
                    student.setEmailPro(studentDto.getEmailPro());
                    student.setEmailPerso(studentDto.getEmailPerso());
                    student.setPhoneNumber(studentDto.getPhoneNumber());
                    student.setAddress(studentDto.getAddress());
                    student.setArchive(studentDto.isArchive());

                    StudentEntity updatedStudent = studentRepository.save(student);
                    return Optional.of(studentMapper.toStudentDtoResponse(updatedStudent));
                }).orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage(STUDENT_NOTFOUND, new Object[]{id}, Locale.getDefault())));
    }

    /**
     * Génère automatiquement un `registrationNu` au format ISI0000001, ISI0000002...
     */
    private String generateRegistrationNumber() {
        // Récupérer le dernier étudiant en fonction du registrationNu (trié par ordre décroissant)
        List<StudentEntity> lastStudents = studentRepository.findLastRegistration();

        // Vérification de la récupération du dernier numéro d'inscription
        String lastRegistrationNu = lastStudents.isEmpty() ? "ISI0000000" : lastStudents.get(0).getRegistrationNu();
        System.out.println("Dernier numéro d'inscription trouvé: " + lastRegistrationNu);

        // Extraire le dernier numéro et l'incrémenter
        Pattern pattern = Pattern.compile("ISI(\\d+)");
        Matcher matcher = pattern.matcher(lastRegistrationNu);

        int newNumber = 1;  // Valeur par défaut si aucun numéro n'a été trouvé
        if (matcher.matches()) {
            newNumber = Integer.parseInt(matcher.group(1)) + 1;
        }

        // Formatter le nouveau numéro sous la forme ISI000000X
        String newRegistrationNu = String.format("ISI%07d", newNumber);

        // Vérification du nouveau numéro généré
        System.out.println("Nouveau numéro généré: " + newRegistrationNu);

        return newRegistrationNu;
    }
}



