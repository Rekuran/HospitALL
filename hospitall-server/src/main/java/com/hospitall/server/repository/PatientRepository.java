package com.hospitall.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospitall.server.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPatientId(String patientId);
    boolean existsByPatientId(String patientId);
    void deleteByPatientId(String patientId);
    java.util.List<Patient> findAllByOrderByGenderAsc();
}
