package com.example.patternslabs.services;

import com.example.patternslabs.models.Patient;
import com.example.patternslabs.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public void createPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient getPatient(int id) {
        return patientRepository.getReferenceById(id);
    }

    public boolean hasPatient(int id) {
        return patientRepository.existsById(id);
    }

    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }

}
