package com.yourispen.studentms.registrations.dto.responses;


import lombok.*;


import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDtoResponse {


    private Long id;
    private Date date;
    private String description;
    private boolean archive;
    private Long studentId;
    private Long classId;
    private Long academicYearId;
}
