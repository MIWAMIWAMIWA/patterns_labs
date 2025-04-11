package com.example.patternslabs.repositories;

import com.example.patternslabs.models.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistRepository extends JpaRepository<Dentist, Integer> {
}
