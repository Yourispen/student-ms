package com.yourispen.studentms.academicyears.entities;


import com.yourispen.studentms.registrations.entities.RegistrationEntity;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "academic_years")
public class AcademicYearEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;


    private String description;


    private boolean archive;


    @OneToMany(mappedBy = "academicYear", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistrationEntity> registrations;
}


