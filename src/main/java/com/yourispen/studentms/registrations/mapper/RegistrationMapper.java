package com.yourispen.studentms.registrations.mapper;


import com.yourispen.studentms.registrations.dto.requests.RegistrationDtoRequest;
import com.yourispen.studentms.registrations.dto.responses.RegistrationDtoResponse;
import com.yourispen.studentms.registrations.entities.RegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;


@Mapper(componentModel = "spring")
public interface RegistrationMapper {


    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "classId", target = "classEntity.id")
    @Mapping(source = "academicYearId", target = "academicYear.id")
    RegistrationEntity toRegistrationEntity(RegistrationDtoRequest registrationDto);


    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "classEntity.id", target = "classId")
    @Mapping(source = "academicYear.id", target = "academicYearId")
    RegistrationDtoResponse toRegistrationDtoResponse(RegistrationEntity registrationEntity);


    List<RegistrationDtoResponse> toRegistrationDtoResponseList(List<RegistrationEntity> registrationEntityList);
}


