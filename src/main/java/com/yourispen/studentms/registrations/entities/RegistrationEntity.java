package com.yourispen.studentms.registrations.entities;


import com.yourispen.studentms.students.entities.StudentEntity;
import com.yourispen.studentms.classes.entities.ClassEntity;
import com.yourispen.studentms.academicyears.entities.AcademicYearEntity;
import jakarta.persistence.*;
import lombok.*;


import java.util.Date;


@Entity
@Table(name = "registrations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;


    @Column(nullable = false)
    private String description;


    @Column(nullable = false)
    private boolean archive;


    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;


    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity;


    @ManyToOne
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYearEntity academicYear;
}
