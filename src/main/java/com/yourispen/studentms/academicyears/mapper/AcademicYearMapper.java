package com.yourispen.studentms.academicyears.mapper;


import com.yourispen.studentms.academicyears.dto.requests.AcademicYearDtoRequest;
import com.yourispen.studentms.academicyears.dto.responses.AcademicYearDtoResponse;
import com.yourispen.studentms.academicyears.entities.AcademicYearEntity;
import org.mapstruct.Mapper;


import java.util.List;


@Mapper(componentModel = "spring")
public interface AcademicYearMapper {
    AcademicYearEntity toAcademicYearEntity(AcademicYearDtoRequest request);
    AcademicYearDtoResponse toAcademicYearDtoResponse(AcademicYearEntity entity);
    List<AcademicYearDtoResponse> toAcademicYearDtoResponseList(List<AcademicYearEntity> entityList);
}
