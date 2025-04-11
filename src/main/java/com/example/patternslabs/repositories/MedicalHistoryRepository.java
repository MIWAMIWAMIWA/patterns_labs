package com.example.patternslabs.repositories;

import com.example.patternslabs.models.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
}