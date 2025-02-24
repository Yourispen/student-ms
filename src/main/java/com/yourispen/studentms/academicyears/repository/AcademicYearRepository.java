package com.yourispen.studentms.academicyears.repository;


import com.yourispen.studentms.academicyears.entities.AcademicYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYearEntity, Long> {
    Optional<AcademicYearEntity> findByName(String name);
}
