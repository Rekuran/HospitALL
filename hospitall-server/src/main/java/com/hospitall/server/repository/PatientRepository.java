package com.hospitall.server.repository;

import com.hospitall.server.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPatientId(String patientId);
    boolean existsByPatientId(String patientId);
    void deleteByPatientId(String patientId);
}
