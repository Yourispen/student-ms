package com.yourispen.studentms.registrations.dto.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDtoRequest {


    @NotNull(message = "La date d'inscription est requise!")
    private Date date;


    @NotBlank(message = "La description est requise!")
    private String description;


    private boolean archive;


    @NotNull(message = "L'ID de l'étudiant est requis!")
    private Long studentId;


    @NotNull(message = "L'ID de la classe est requis!")
    private Long classId;


    @NotNull(message = "L'ID de l'année académique est requis!")
    private Long academicYearId;
}
