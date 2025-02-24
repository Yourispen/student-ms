package com.yourispen.studentms.students.entities;

import com.yourispen.studentms.registrations.entities.RegistrationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "students")
public class StudentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String emailPro;

    @Column(nullable = false, length = 4096)
    private String token;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String emailPerso;
    private String phoneNumber;
    private String address;
    private boolean archive;

    @Column(nullable = false, unique = true)
    private String registrationNu;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistrationEntity> registrations;
}



