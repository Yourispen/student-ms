package com.yourispen.studentms.students.dto.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDtoResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailPro;
    private String token;
    private String emailPerso;
    private String phoneNumber;
    private String address;
    private boolean archive;
    private String registrationNu;
    private List<Long> registrationIds;
}



