package com.yourispen.studentms.classes.services;


import com.yourispen.studentms.classes.dto.requests.ClassDtoRequest;
import com.yourispen.studentms.classes.dto.responses.ClassDtoResponse;


import java.util.List;
import java.util.Optional;


public interface ClassService {
    Optional<ClassDtoResponse> saveClass(ClassDtoRequest classDto);
    List<ClassDtoResponse> getAllClasses();
    Optional<ClassDtoResponse> getClassById(Long id);
    boolean deleteClass(Long id);
    Optional<ClassDtoResponse> updateClass(Long id, ClassDtoRequest classDto);
}
