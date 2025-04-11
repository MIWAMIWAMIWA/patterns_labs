package com.example.patternslabs.repositories;

import com.example.patternslabs.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
