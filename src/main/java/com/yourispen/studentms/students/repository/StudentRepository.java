package com.yourispen.studentms.students.repository;

import com.yourispen.studentms.students.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByEmailPro(String emailPro);
    Optional<StudentEntity> findTopByOrderByIdDesc();

    @Query("SELECT s FROM StudentEntity s WHERE s.registrationNu LIKE 'ISI%' ORDER BY s.registrationNu DESC")
    List<StudentEntity> findLastRegistration();

}
