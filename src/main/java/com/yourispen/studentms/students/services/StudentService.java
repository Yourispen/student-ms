package com.yourispen.studentms.students.services;

import com.yourispen.studentms.students.dto.requests.StudentDtoRequest;
import com.yourispen.studentms.students.dto.responses.StudentDtoResponse;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Optional<StudentDtoResponse> saveStudent(StudentDtoRequest studentDto);
    List<StudentDtoResponse> getAllStudents();
    Optional<StudentDtoResponse> getStudentById(Long id);
    boolean deleteStudent(Long id);
    Optional<StudentDtoResponse> updateStudent(Long id, StudentDtoRequest studentDto);
}
