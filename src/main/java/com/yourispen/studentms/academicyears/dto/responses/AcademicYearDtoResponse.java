package com.yourispen.studentms.academicyears.dto.responses;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYearDtoResponse {
    private Long id;
    private String name;
    private String description;
    private boolean archive;
}


