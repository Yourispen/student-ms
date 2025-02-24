package com.yourispen.studentms.classes.mapper;


import com.yourispen.studentms.classes.dto.requests.ClassDtoRequest;
import com.yourispen.studentms.classes.dto.responses.ClassDtoResponse;
import com.yourispen.studentms.classes.entities.ClassEntity;
import org.mapstruct.Mapper;


import java.util.List;


@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassEntity toClassEntity(ClassDtoRequest classDto);
    ClassDtoResponse toClassDtoResponse(ClassEntity classEntity);
    List<ClassDtoResponse> toClassDtoResponseList(List<ClassEntity> classEntities);
}
