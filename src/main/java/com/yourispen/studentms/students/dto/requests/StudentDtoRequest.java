package com.yourispen.studentms.students.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDtoRequest {

    @NotBlank(message = "Le prénom est requis!")
    private String firstName;

    @NotBlank(message = "Le nom est requis!")
    private String lastName;

    @NotBlank(message = "L'email professionnel est requis!")
    private String emailPro;

    @NotBlank(message = "Le token est est requis!")
    private String token;

    private String emailPerso;
    private String phoneNumber;
    private String address;
    private boolean archive;
    //private String registrationNu;
}
