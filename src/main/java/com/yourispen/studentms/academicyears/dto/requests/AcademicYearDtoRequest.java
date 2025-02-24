package com.yourispen.studentms.academicyears.dto.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYearDtoRequest {


    @NotBlank(message = "Le nom de l'année académique est requis!")
    private String name;


    private String description;


    private boolean archive;
}
