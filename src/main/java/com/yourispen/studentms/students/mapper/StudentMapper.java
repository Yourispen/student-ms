package com.yourispen.studentms.students.mapper;

import com.yourispen.studentms.students.dto.requests.StudentDtoRequest;
import com.yourispen.studentms.students.dto.responses.StudentDtoResponse;
import com.yourispen.studentms.students.entities.StudentEntity;
import com.yourispen.studentms.registrations.entities.RegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(source = "registrations", target = "registrationIds", qualifiedByName = "mapRegistrationIds")
    StudentDtoResponse toStudentDtoResponse(StudentEntity studentEntity);

    StudentEntity toStudentEntity(StudentDtoRequest studentDto);

    List<StudentDtoResponse> toStudentDtoResponseList(List<StudentEntity> studentEntities);

    @Named("mapRegistrationIds")
    default List<Long> mapRegistrationIds(List<RegistrationEntity> registrations) {
        if (registrations == null) {
            return Collections.emptyList();  // Retourne une liste vide si `registrations` est null
        }
        return registrations.stream()
                .map(RegistrationEntity::getId)
                .collect(Collectors.toList());
    }
}



