package com.yourispen.studentms.registrations.repository;


import com.yourispen.studentms.registrations.entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {
}
