package com.yourispen.studentms.classes.dto.responses;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDtoResponse {


    private Long id;
    private String name;
    private String description;
    private boolean archive;
}


