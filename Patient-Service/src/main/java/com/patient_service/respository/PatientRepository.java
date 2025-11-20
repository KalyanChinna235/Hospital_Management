package com.patient_service.respository;

import com.patient_service.dto.PatientRequest;
import com.patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
}
