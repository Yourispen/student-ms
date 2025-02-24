package com.yourispen.studentms.classes.entities;


import com.yourispen.studentms.registrations.entities.RegistrationEntity;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;


@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;


    private String description;


    private boolean archive;


    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistrationEntity> registrations;
}


