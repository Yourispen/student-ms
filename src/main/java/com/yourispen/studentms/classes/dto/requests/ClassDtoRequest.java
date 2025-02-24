package com.yourispen.studentms.classes.dto.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDtoRequest {


    @NotBlank(message = "Le nom de la classe est requis!")
    private String name;


    private String description;
    private boolean archive;
}
